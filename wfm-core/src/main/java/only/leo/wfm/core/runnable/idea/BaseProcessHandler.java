package only.leo.wfm.core.runnable.idea;

import only.leo.wfm.common.Constants;
import only.leo.wfm.common.FileSinkOperation;
import only.leo.wfm.common.FileType;
import only.leo.wfm.common.MemoryCache;
import only.leo.wfm.common.beans.FileEvent;
import only.leo.wfm.common.beans.FileMeta;
import only.leo.wfm.common.beans.IndexDirectoryVO;
import only.leo.wfm.common.util.FileUtil;
import only.leo.wfm.core.Pipeline;
import only.leo.wfm.core.service.FileDetectedService;
import only.leo.wfm.core.service.IIndexService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @Author: LIBAO
 */
@Component
public class BaseProcessHandler implements ProcessHandler, InitializingBean , Closeable {
    private Logger logger = Logger.getLogger(this.getClass());
    private  BufferedWriter myWriter;
    private  Reader reader;
    private  Reader errorReader;
    @Autowired
    private FileDetectedService fileDetectedService;
    @Autowired
    private Pipeline<FileEvent> pipeline;
    @Autowired
    private IIndexService indexService;
    private final char[] myInputBuffer = new char[8192];
    private final StringBuilder myLineBuffer = new StringBuilder();
    private boolean myCarry;
    private ProcessEvent.WatcherOp myLastOp;
    private ProcessEvent myLastEvent;
    private static final Charset CHARSET =
            SystemInfo.isWindows || SystemInfo.isMac ? StandardCharsets.UTF_8 : CharsetToolkit.getPlatformCharset();
    private Path myExecutable;
    private  Process process;
    private Future taskFuture;
    private Consumer<ProcessEvent> consumer;
    private ExecutorService waitForThread = Executors.newSingleThreadExecutor();
    private static final String ROOTS_COMMAND = "ROOTS";
    private static final String EXIT_COMMAND = "EXIT";
    private static final String START_COMMAND = "#";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private void copyfsnotifier() throws IOException{
        String path = null;
        String name = null;
        if(SystemInfo.isWindows){
            if(CpuArch.isIntel64()){
                path = "bin/win/amd64/fsnotifier.exe";
            }
            if(CpuArch.isArm64()){
                path = "bin/win/aarch64/fsnotifier.exe";
            }
            name = "fsnotifier.exe";
        } else if (SystemInfo.isMac) {
            path = "bin/mac/fsnotifier";
            name = "fsnotifier";
        }else if (SystemInfo.isLinux) {
            if(CpuArch.isIntel64()){
                path = "bin/linux/amd64/fsnotifier";
            }
            if(CpuArch.isArm64()){
                path = "bin/linux/aarch64/fsnotifier";
            }
            name = "fsnotifier";
        }
        Path filePath;
        InputStream inputStream = BaseProcessHandler.class.getClassLoader().getResourceAsStream(path);
        if (inputStream != null) {
            String userDir = System.getProperty("user.dir");
            filePath = Paths.get(userDir,"SYSTEM","bin", name);
            File file = new File(filePath.toAbsolutePath().toString());
            if(!file.exists()){
                file.mkdirs();
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                file.setExecutable(true);
            }
        } else {
            throw new IOException("Resource file not found");
        }
        this.myExecutable = filePath;

    }
    @Override
    public void afterPropertiesSet() throws Exception {
        copyfsnotifier();
        attachConsumer();
        pipeline.start();
        taskFuture = executorService.submit(()->{
            try {
                startProcess(false);
            } catch (IOException e) {
                logger.error("start file watcher failed",e);
            }
        });
    }

    private void writeLine(String line) throws IOException {
        myWriter.write(line);
        myWriter.newLine();
        myWriter.flush();
    }
    private void sendText( StringBuilder line) {
        String text = line.toString();
        line.setLength(0);
        notifyTextAvailable(text);
    }

    public void listenPath(List<String> paths) throws IOException{
        writeLine(ROOTS_COMMAND);
        for(String path:paths){
            writeLine(path);
        }
        writeLine(START_COMMAND);
    }

    public boolean sendIncompleteLines() { return false; }
    public boolean withSeparators() { return false; }
    public boolean splitToLines() { return true; }
    private void processInput(char[] buffer, StringBuilder line, int n) {
        if (splitToLines()) {
            for (int i = 0; i < n; i++) {
                char c;
                if (i == 0 && myCarry) {
                    c = '\r';
                    i--;
                    myCarry = false;
                }
                else {
                    c = buffer[i];
                }

                if (c == '\r') {
                    if (i + 1 == n) {
                        myCarry = true;
                        continue;
                    }
                    else if (buffer[i + 1] == '\n') {
                        continue;
                    }
                }

                if (c != '\n' || sendIncompleteLines() || withSeparators()) {
                    line.append(c);
                }

                if (c == '\n') {
                    sendText(line);
                }
            }

            if (line.length() > 0 && sendIncompleteLines()) {
                sendText(line);
            }
        }
        else {
            notifyTextAvailable(new String(buffer, 0, n));
        }
    }
    protected void flush() {
        if (myLineBuffer.length() > 0) {
            sendText(myLineBuffer);
        }
    }
    private void addListener(Process process){
        waitForThread.submit(()->{
            int exitCode = 0;
            try {
                exitCode = process.waitFor();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            notifyTerminated(exitCode);
        });
    }
    public void startProcess(boolean restart) throws IOException{
        if(restart){
            try {
                if(reader!=null){
                    reader.close();
                }
                if(errorReader!=null){
                    errorReader.close();
                }
                if(myWriter!=null){
                    myWriter.close();
                }
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        initProcess();
        if(restart){
            listenPath(Arrays.asList(fileDetectedService.getWatchPaths()));
        }
        startNotify();
    }
    private void setConsumer(Consumer<ProcessEvent> consumer){
        this.consumer = consumer;
    }
    public void attachConsumer() {
        this.setConsumer((event)->{
            if(!fileDetectedService.isWatch(event.getValue())) {
                return;
            }
            ProcessEvent.WatcherOp op = event.getOp();
            String location = event.getValue();
            File cFile = new File(location);
            FileMeta fileMeta;
            if(op == ProcessEvent.WatcherOp.DELETE){
                FileType fileType = FileUtil.getFileType(cFile.getName());
                if((FileType.NO_INDEX.equals(fileType)||FileType.OTHER.equals(fileType))&& MemoryCache.cachePath2Id.containsKey(location)){
                    fileType = FileType.DIRECTORY;
                }
                if(fileType == FileType.NO_INDEX) return;
                fileMeta = new FileMeta(fileType,location);
            }else {
                FileType fileType = FileUtil.getFileType(cFile);
                if(fileType == FileType.NO_INDEX) return;
                fileMeta = FileMeta.fromLocalFile(cFile);
            }
            switch (op){
                case DELETE:
                    pipeline.submit(new FileEvent(fileMeta, FileSinkOperation.REMOVE));
                    break;
                case CREATE:
                    IndexDirectoryVO vo = indexService.queryByLocation(fileMeta.getLocation());
                    if(vo==null) return;
                    fileMeta.setIndexId(vo.getId());
                    fileMeta.setLastModifyTime(new Date());
                    if(fileMeta.getDirectory()){
                        File[] fileList = cFile.listFiles();
                        if(fileList!=null&&fileList.length>0){
                            try {
                                indexService.index(fileMeta.getLocation(),vo.toBean());
                            } catch (Exception e) {
                                logger.error("error occurs when scan path="+fileMeta.getLocation());
                            }
                        }
                    }
                    if (cFile.exists()){
                        pipeline.submit(new FileEvent(fileMeta, FileSinkOperation.ADD));
                    }
                    break;
                case CHANGE:
                    if(!fileMeta.getDirectory() && cFile.exists()){
                        fileMeta.setLastModifyTime(new Date());
                        pipeline.submit(new FileEvent(fileMeta, FileSinkOperation.UPDATE));
                    }
                    break;
            }
        });

    }
    private void initProcess() throws IOException{
        process = new ProcessBuilder(myExecutable.toAbsolutePath().toString()).start();
        addListener(process);
        myWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), CHARSET));
        reader = new BaseInputStreamReader(process.getInputStream(),CHARSET);
        errorReader = new BaseInputStreamReader(process.getErrorStream(),CHARSET);
    }
    private void startNotify(){
        try {
            readAvailableBlocking();
        }catch (Exception e) {
           logger.error(e.getMessage(),e);
        }finally {
            flush();
        }

    }
    protected final boolean readAvailableBlocking() throws IOException {
        boolean read = false;
        try {
            int n;
            while ((n = reader.read(myInputBuffer)) >= 0) {
                if (n > 0) {
                    read = true;
                    processInput(myInputBuffer, myLineBuffer, n);
                }
            }
        } finally {
            if (myCarry) {
                myLineBuffer.append('\r');
                myCarry = false;
            }
            if (myLineBuffer.length() > 0) {
                sendText(myLineBuffer);
            }
        }
        return read;
    }
    @Override
    public void notifyTerminated(int exitCode) {
        logger.warn("file watcher exit with code " + exitCode);
        taskFuture.cancel(true);
        taskFuture = executorService.submit(()->{
            try {
                startProcess(true);
            } catch (IOException e) {
                logger.error("restart file watcher failed",e);
            }
        });
    }

    @Override
    public void onClose() {
        try {
            close();
        } catch (IOException e) {
        }
    }

    @Override
    public void notifyTextAvailable(String line) {
        if(myLastOp == null){
            ProcessEvent.WatcherOp watcherOp;
            try {
                watcherOp = ProcessEvent.WatcherOp.valueOf(line);
            }
            catch (IllegalArgumentException e) {
                String message = "Illegal watcher command: '" + line + "'";
                if (line.length() <= 20) message += " " + Arrays.toString(line.chars().toArray());
                logger.error(message);
                return;
            }
            myLastOp = watcherOp;
        }else{
            ProcessEvent event = new ProcessEvent(myLastOp,line);
            myLastOp = null;
            processEvent(event);
        }
//        System.out.println(line);
    }
    protected void processEvent(ProcessEvent event){
        if(event.equals(myLastEvent)) return;
        myLastEvent=event;
        consumer.accept(event);
    }

    @Override
    public void close() throws IOException {
        process.destroy();
        logger.warn("close sub-process successfully");
    }
}
