package only.leo.wfm.common.beans;

import only.leo.wfm.common.util.FileUtil;

import java.util.Date;
import java.util.Objects;

public class FileMetaDO {
    private String id;

    private String fileName;

    private Long fileSize;

    private Byte fileType;

    private Short indexId;

    private Integer directoryId;

    private Date fileModifyTime;
    public FileMetaVO toVO(){
        FileMetaVO fileMetaVO = new FileMetaVO();
        fileMetaVO.setFileModifyTime(fileModifyTime);
        fileMetaVO.setFileName(fileName);
        fileMetaVO.setId(id);
        fileMetaVO.setFileSize(fileSize);
        fileMetaVO.setFileType(FileUtil.getFileType((byte)fileType).getName());
        fileMetaVO.setIndexId(indexId);
        return fileMetaVO;
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

    public Byte getFileType() {
        return fileType;
    }

    public void setFileType(Byte fileType) {
        this.fileType = fileType;
    }

    public Short getIndexId() {
        return indexId;
    }

    public void setIndexId(Short indexId) {
        this.indexId = indexId;
    }

    public Integer getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(Integer directoryId) {
        this.directoryId = directoryId;
    }

    public Date getFileModifyTime() {
        return fileModifyTime;
    }

    public void setFileModifyTime(Date fileModifyTime) {
        this.fileModifyTime = fileModifyTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        FileMetaDO that = (FileMetaDO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}