package only.leo.wfm.common.beans;

public class BaseConfigDO {
    private Integer id;

    private String domain;

    private Boolean init;

    private String otherFiles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

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
        this.otherFiles = otherFiles == null ? null : otherFiles.trim();
    }
    public BaseConfigVO toVO(){
        BaseConfigVO vo = new BaseConfigVO();
        vo.setOtherFiles(otherFiles);
        vo.setDomain(domain);
        vo.setInit(init);
        return vo;
    }
}