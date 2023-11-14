package only.leo.wfm.core.scanner;

import only.leo.wfm.common.FileType;
import only.leo.wfm.common.beans.IndexDirectory;
import only.leo.wfm.common.util.FileUtil;
import only.leo.wfm.core.*;
import only.leo.wfm.core.service.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LEO
 * @Date: 2021/8/16 15:31
 */
public class LocalFileScanner extends AbstractScanner<File>{
    private static Logger logger = Logger.getLogger(LocalFileScanner.class);
    /**
     * 构造一个本地扫描器
     * @param scanPath 要扫描的路径
     * @param indexDirectory 所属的index目录
     * @param pipeline
     * @param fileDetectedService
     */
    public LocalFileScanner(String scanPath, IndexDirectory indexDirectory, Pipeline pipeline, FileDetectedService fileDetectedService
            , IDirectoryService directoryService, IFileManagerService fileManagerService) {
        this.scanPath = scanPath;
        this.indexDirectory = indexDirectory;
        this.fileDetectedService = fileDetectedService;
        this.pipeline = pipeline;
        this.directoryService = directoryService;
        this.fileManagerService = fileManagerService;
    }
    @Override
    public void startInner() throws Exception{
            scan(new File(scanPath));
    }
    private class InnerScanner implements Runnable{
        private int deep;
        private File currentDir;
        public InnerScanner(int deep, File currentDir) {
            this.deep = deep;
            this.currentDir = currentDir;
        }

        @Override
        public void run() {
            try {
                deep++;
                if(deep>maxDeeps){
                    return;
                }
                if(!currentDir.isDirectory()){
                    throw new ScanFilesException('"' + currentDir.getAbsolutePath() + '"' + " input path is not a Directory , please input the right path of the Directory.");
                }
                File [] fileList = currentDir.listFiles();
                if(fileList==null){
                    return;
                }
                List<File> submitFiles = new ArrayList<>();
                List<File> toScanDirectories = new ArrayList<>();
                boolean f = false;
                submitFiles.add(currentDir);
                for(int i = 0; i < fileList.length; i ++){
                    File file = fileList[i];
                    if(file==null||!file.exists()||FileUtil.getFileType(file)==FileType.NO_INDEX) {
                        continue;
                    }
                    if(file.isDirectory()){
                        if(filter(file)){
                            //递归
                            toScanDirectories.add(file);
                        }
                    } else {
                        submitFiles.add(file);
                        f=true;
                    }
                }
                if(f){
                    submit(submitFiles);
                }
                for(File dir :toScanDirectories){
                    try {
                        scanWorkerExecutor.submit(new InnerScanner(deep,dir));
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                }
            } catch (ScanFilesException e) {
                logger.error(e.getMessage());
            }
        }
    }
   //TODO 重做
    void scan(File currentDir) throws ScanFilesException{
        scanWorkerExecutor.submit(new InnerScanner(0,currentDir));
    }

    @Override
    void submit(List<File> files) {
        //重新扫描文件夹 提交文件夹和文件
        try {
            preSinkWorker.submit(new SubmitLocalFileRunner(files,indexDirectory,pipeline,fileDetectedService,directoryService,fileManagerService));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    boolean filter(File dir){
        String dirName = dir.getName();
        return !dirName.startsWith(".")&&!dirName.startsWith("@")&&!dirName.startsWith("$")&&!dirName.startsWith("_@")&&!dirName.equalsIgnoreCase("node_modules");
    }
}
