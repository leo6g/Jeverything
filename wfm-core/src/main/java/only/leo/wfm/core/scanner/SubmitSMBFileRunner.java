package only.leo.wfm.core.scanner;

import jcifs.smb.SmbFile;
import only.leo.wfm.common.beans.FileEvent;
import only.leo.wfm.common.beans.FileMeta;
import only.leo.wfm.common.beans.IndexDirectory;
import only.leo.wfm.core.Pipeline;
import only.leo.wfm.core.service.FileDetectedService;
import only.leo.wfm.core.service.IDirectoryService;
import only.leo.wfm.core.service.IFileManagerService;

import java.util.List;

/**
 * @Author: LEO
 * @Date: 2021/9/17 17:16
 */
public class SubmitSMBFileRunner extends BaseSubmit<SmbFile> implements Runnable {

    public SubmitSMBFileRunner(List<SmbFile> submitFiles, IndexDirectory indexDirectory, Pipeline<FileEvent> pipeline, FileDetectedService fileDetectedService
            , IDirectoryService directoryService, IFileManagerService fileManagerService) {
        super(submitFiles,indexDirectory,pipeline,fileDetectedService,directoryService,fileManagerService);
    }

    /**
     * 多线程 辅助扫描文件夹 提交文件信息
     * submitFiles 大小最低为2，索引0为文件夹 索引1+ 文件夹下的文件
     */
    @Override
    public void run() {
        try {
            if(submitFiles==null||submitFiles.size()<2) return;
            loadSignatures(submitFiles.get(0).getCanonicalPath());
            compareAndSubmit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    FileMeta toFileMeta(SmbFile file) {
        return FileMeta.fromSMBFile(file);
    }
}
