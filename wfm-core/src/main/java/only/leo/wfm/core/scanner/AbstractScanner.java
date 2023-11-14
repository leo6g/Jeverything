package only.leo.wfm.core.scanner;

import only.leo.wfm.common.beans.FileEvent;
import only.leo.wfm.common.beans.IndexDirectory;
import only.leo.wfm.core.Pipeline;
import only.leo.wfm.core.ScanFilesException;
import only.leo.wfm.core.ScannerState;
import only.leo.wfm.core.service.FileDetectedService;
import only.leo.wfm.core.service.IDirectoryService;
import only.leo.wfm.core.service.IFileManagerService;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AbstractScanner<T> implements Scanner {
    private ScannerState state;
    protected String scanPath;
    protected IndexDirectory indexDirectory;
    protected static ThreadPoolExecutor preSinkWorker = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()*2,
            Runtime.getRuntime().availableProcessors()*3,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1024 * 10),
            new ThreadPoolExecutor.CallerRunsPolicy()
           ){
        {
            allowCoreThreadTimeOut(true);
        }
    };
    protected static ThreadPoolExecutor scanWorkerExecutor = new ThreadPoolExecutor(2,
            4,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1024*10),
            new ThreadPoolExecutor.CallerRunsPolicy()){
        {
            allowCoreThreadTimeOut(true);
        }
    };
    protected Pipeline<FileEvent> pipeline;
    protected FileDetectedService fileDetectedService;
    protected IFileManagerService fileManagerService;
    protected IDirectoryService directoryService;
    //扫描最大深度
    protected int maxDeeps = 50;
    @Override
    public void stop() {
        setState(ScannerState.STOP);
    }

    @Override
    public void shutDown() {
        setState(ScannerState.TERMINATE);
    }

    /**
     * 主扫描
     * @param currentDir 当前扫描目录
     * @throws ScanFilesException
     */
    abstract void scan(T currentDir) throws ScanFilesException;
    abstract void submit(List<T> files);

    @Override
    final public void start() throws Exception{
        pipeline.start();
        setState(ScannerState.START);
        startInner();
    }
    abstract void startInner() throws Exception;
    /**
     * true-通过
     * @param file
     * @return
     */
    abstract boolean filter(T file);
    public ScannerState getState() {
        return state;
    }

    public void setState(ScannerState state) {
        this.state = state;
    }

}
