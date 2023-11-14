package only.leo.wfm.common.beans;

import java.util.Date;

public class DirectoryVO {
    private Integer id;
    private Integer parentId;

    private Short indexId;
    private String path;

    private Date directoryModifyTime;

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