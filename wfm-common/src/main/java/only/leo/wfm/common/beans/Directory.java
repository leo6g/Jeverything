package only.leo.wfm.common.beans;

import java.util.Date;

public class Directory {
    private Integer id;

    private Integer parentId;

    private Short indexId;

    private String path;

    private Date directoryModifyTime;

    public Directory(Short indexId, String path, Date directoryModifyTime) {
        this.indexId = indexId;
        this.path = path;
        this.directoryModifyTime = directoryModifyTime;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Short getIndexId() {
        return indexId;
    }

    public void setIndexId(Short indexId) {
        this.indexId = indexId;
    }

    public Directory() {
    }

    public Directory(String path) {
        this.path = path;
    }

    public Directory(String path, Date directoryModifyTime) {
        this.path = path;
        this.directoryModifyTime = directoryModifyTime;
    }

    public DirectoryDO toDO(){
        DirectoryDO directoryDO = new DirectoryDO();
        directoryDO.setDirectoryModifyTime(directoryModifyTime);
        directoryDO.setId(id);
        directoryDO.setPath(path);
        directoryDO.setIndexId(indexId);
        directoryDO.setParentId(parentId);
        return directoryDO;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Date getDirectoryModifyTime() {
        return directoryModifyTime;
    }

    public void setDirectoryModifyTime(Date directoryModifyTime) {
        this.directoryModifyTime = directoryModifyTime;
    }
}