package only.leo.wfm.common.beans;

import only.leo.wfm.common.ProtocolType;

import java.util.Date;
import java.util.Objects;

public class IndexDirectoryVO {
    private Short id;
    private String path;
    private Boolean listen;
    private Boolean isRemote;
    private Boolean loading = false;
    private String protocol;
    private Long fileCount;
    private Date indexTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        IndexDirectoryVO that = (IndexDirectoryVO) o;
        return id.equals(that.id) &&
                path.equals(that.path) &&
                isRemote.equals(that.isRemote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, path, isRemote);
    }

    public Boolean getListen() {
        return listen;
    }

    public void setListen(Boolean listen) {
        this.listen = listen;
    }

    public Boolean getLoading() {
        return loading;
    }

    public void setLoading(Boolean loading) {
        this.loading = loading;
    }

    public Long getFileCount() {
        return fileCount;
    }

    public void setFileCount(Long fileCount) {
        this.fileCount = fileCount;
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

    public Boolean getIsRemote() {
        return isRemote;
    }

    public void setIsRemote(Boolean isRemote) {
        this.isRemote = isRemote;
    }

    public String getProtocol() {
        return protocol;
    }
    public IndexDirectory toBean(){
        IndexDirectory indexDirectory = new IndexDirectory();
        indexDirectory.setProtocol(ProtocolType.fromName(protocol).getCode());
        indexDirectory.setIsRemote(isRemote);
        indexDirectory.setPath(path);
        indexDirectory.setIndexTime(indexTime);
        indexDirectory.setId(id);
        indexDirectory.setListen(listen);
        return indexDirectory;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Date getIndexTime() {
        return indexTime;
    }

    public void setIndexTime(Date indexTime) {
        this.indexTime = indexTime;
    }
}