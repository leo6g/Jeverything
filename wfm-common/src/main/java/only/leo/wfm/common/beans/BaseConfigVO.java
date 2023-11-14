package only.leo.wfm.common.beans;


public class BaseConfigVO {
    private String otherFiles;
    private String domain;
    private Boolean init;

    public Boolean getInit() {
        return init;
    }

    public void setInit(Boolean init) {
        this.init = init;
    }

    public String getOtherFiles() {
        return otherFiles;
    }

    public void setOtherFiles(String otherFiles) {
        this.otherFiles = otherFiles;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
