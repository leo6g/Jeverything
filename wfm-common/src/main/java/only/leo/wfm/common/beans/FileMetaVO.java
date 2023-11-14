package only.leo.wfm.common.beans;

import java.util.Date;

public class FileMetaVO {
    private String id;

    private String fileName;

    private String path;

    private Boolean isRemote;

    private Long fileSize;

    private String fileType;
    private Short indexId;
    private Date fileModifyTime;

    public Short getIndexId() {
        return indexId;
    }

    public void setIndexId(Short indexId) {
        this.indexId = indexId;
    }

    public Boolean getRemote() {
        return isRemote;
    }

    public void setRemote(Boolean remote) {
        isRemote = remote;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }


    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getFileModifyTime() {
        return fileModifyTime;
    }

    public void setFileModifyTime(Date fileModifyTime) {
        this.fileModifyTime = fileModifyTime;
    }
}