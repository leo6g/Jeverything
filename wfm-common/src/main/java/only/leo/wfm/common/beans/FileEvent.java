package only.leo.wfm.common.beans;

import only.leo.wfm.common.FileSinkOperation;

/**
 * @Author: LEO
 * @Date: 2021/9/2 13:36
 */
public class FileEvent {
    private FileMeta fileMeta;
    private FileSinkOperation fileSinkOperation;

    public FileEvent(FileMeta fileMeta, FileSinkOperation fileSinkOperation) {
        this.fileMeta = fileMeta;
        this.fileSinkOperation = fileSinkOperation;
    }

    public FileMeta getFileMeta() {
        return fileMeta;
    }

    public void setFileMeta(FileMeta fileMeta) {
        this.fileMeta = fileMeta;
    }

    public FileSinkOperation getFileSinkOperation() {
        return fileSinkOperation;
    }

    public void setFileSinkOperation(FileSinkOperation fileSinkOperation) {
        this.fileSinkOperation = fileSinkOperation;
    }
}
