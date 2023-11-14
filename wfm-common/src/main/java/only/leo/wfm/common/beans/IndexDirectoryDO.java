package only.leo.wfm.common.beans;

import only.leo.wfm.common.util.FileUtil;

import java.util.Date;

public class IndexDirectoryDO {
    private Short id;

    private String path;

    private Boolean listen;

    private Boolean isRemote;

    private Byte protocol;

    private String authentication;

    private Date indexTime;
    public IndexDirectoryVO toVO(){
        IndexDirectoryVO vo = new IndexDirectoryVO();
        vo.setId(id);
        vo.setIndexTime(indexTime);
        vo.setIsRemote(isRemote);
        vo.setPath(path);
        vo.setListen(listen);
        vo.setProtocol(FileUtil.getProtocolType(protocol).getName());
        return vo;
    }
    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Boolean getListen() {
        return listen;
    }

    public void setListen(Boolean listen) {
        this.listen = listen;
    }

    public Boolean getIsRemote() {
        return isRemote;
    }

    public void setIsRemote(Boolean isRemote) {
        this.isRemote = isRemote;
    }

    public Byte getProtocol() {
        return protocol;
    }

    public void setProtocol(Byte protocol) {
        this.protocol = protocol;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication == null ? null : authentication.trim();
    }

    public Date getIndexTime() {
        return indexTime;
    }

    public void setIndexTime(Date indexTime) {
        this.indexTime = indexTime;
    }
}