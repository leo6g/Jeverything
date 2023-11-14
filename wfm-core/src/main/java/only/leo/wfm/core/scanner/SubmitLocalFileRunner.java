package only.leo.wfm.core.scanner;

import only.leo.wfm.common.beans.FileEvent;
import only.leo.wfm.common.beans.FileMeta;
import only.leo.wfm.common.beans.IndexDirectory;
import only.leo.wfm.core.Pipeline;
import only.leo.wfm.core.service.FileDetectedService;
import only.leo.wfm.core.service.IDirectoryService;
import only.leo.wfm.core.service.IFileManagerService;

import java.io.File;
import java.util.List;

/**
 * @Author: LEO
 * @Date: 2021/9/17 17:16
 */
public class SubmitLocalFileRunner extends BaseSubmit<File> implements Runnable {

    public SubmitLocalFileRunner(List<File> submitFiles, IndexDirectory indexDirectory, Pipeline<FileEvent> pipeline, FileDetectedService fileDetectedService
            , IDirectoryService directoryService, IFileManagerService fileManagerService) {
        super(submitFiles,indexDirectory,pipeline,fileDetectedService,directoryService,fileManagerService);
    }

    /**
     * 多线程 辅助扫描文件夹 提交文件信息
     * submitFiles 大小最低为2，索引0为文件夹 索引1+ 文件夹下的文件
     * 远程目录不支持实时检测，需要对比文件目录来判断变动
     */
    @Override
    public void run() {
        try {
            if(submitFiles==null||submitFiles.size()<2) {
                return;
            }
            loadSignatures(submitFiles.get(0).getAbsolutePath());
            compareAndSubmit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    FileMeta toFileMeta(File file) {
        return FileMeta.fromLocalFile(file);
    }
}
