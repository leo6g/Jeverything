package only.leo.wfm.common.beans;

public class BaseConfig {
    private Integer id;
    private String otherFiles;
    private String domain;
    private Boolean init;

    public Boolean getInit() {
        return init;
    }

    public void setInit(Boolean init) {
        this.init = init;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BaseConfigDO toDO(){
       BaseConfigDO baseConfigDO = new BaseConfigDO();
       baseConfigDO.setDomain(this.domain);
       baseConfigDO.setId(this.id);
       baseConfigDO.setOtherFiles(this.otherFiles);
       baseConfigDO.setInit(this.init);
       return baseConfigDO;
   }
}
