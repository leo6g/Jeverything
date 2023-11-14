package only.leo.wfm.common.beans;

import java.util.Date;

public class IndexDirectory {
    private Short id;

    private String path;

    private Boolean isRemote;

    private Boolean listen;

    private Byte protocol;

    private String authentication;

    private Date indexTime;

    public Boolean getListen() {
        return listen;
    }

    public void setListen(Boolean listen) {
        this.listen = listen;
    }

    public IndexDirectory(Short id, String path, Boolean isRemote, Boolean listen) {
        this.id = id;
        this.path = path;
        this.isRemote = isRemote;
        this.listen = listen;
    }

    public IndexDirectory() {
    }

    public IndexDirectoryDO toDO(){
        IndexDirectoryDO indexDirectoryDO = new IndexDirectoryDO();
        indexDirectoryDO.setProtocol(protocol);
        indexDirectoryDO.setIsRemote(isRemote);
        indexDirectoryDO.setPath(path);
        indexDirectoryDO.setIndexTime(indexTime);
        indexDirectoryDO.setAuthentication(authentication);
        indexDirectoryDO.setId(id);
        indexDirectoryDO.setListen(listen);
        return indexDirectoryDO;
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
        this.path = path;
    }

    public Boolean getRemote() {
        return isRemote;
    }

    public void setRemote(Boolean remote) {
        isRemote = remote;
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

    @Override
    public String toString() {
        return "IndexDirectory{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", isRemote=" + isRemote +
                ", protocol=" + protocol +
                ", authentication='" + authentication + '\'' +
                ", indexTime=" + indexTime +
                '}';
    }
}