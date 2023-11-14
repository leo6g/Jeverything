package only.leo.wfm.common;

public enum PlatformSystem {
    LINUX("Linux"),WINDOWS("Windows");
    private String flag;

    PlatformSystem(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
