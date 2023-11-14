package only.leo.wfm.common.beans;

import java.util.Date;

public class DirectoryDO {
    private Integer id;

    private Integer parentId;

    private Short indexId;

    private String path;

    private Date directoryModifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Date getDirectoryModifyTime() {
        return directoryModifyTime;
    }
    public DirectoryVO toVO(){
        DirectoryVO vo = new DirectoryVO();
        vo.setDirectoryModifyTime(directoryModifyTime);
        vo.setId(id);
        vo.setIndexId(indexId);
        vo.setParentId(parentId);
        vo.setPath(path);
        return vo;
    }
    public void setDirectoryModifyTime(Date directoryModifyTime) {
        this.directoryModifyTime = directoryModifyTime;
    }
}