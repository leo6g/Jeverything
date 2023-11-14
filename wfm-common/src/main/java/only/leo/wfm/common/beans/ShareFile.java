package only.leo.wfm.common.beans;

import java.util.Date;

public class ShareFile {
    private String code;

    private Boolean enable;

    private Date shareTime;

    private String fileName;

    private String fetchCode;

    private Date invalidTime;

    public ShareFileDO toDO(){
        ShareFileDO shareFileDO = new ShareFileDO();
        shareFileDO.setCode(code);
        shareFileDO.setEnable(enable);
        shareFileDO.setShareTime(shareTime);
        shareFileDO.setInvalidTime(invalidTime);
        shareFileDO.setFetchCode(fetchCode);
        shareFileDO.setFileName(fileName);
        return shareFileDO;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFetchCode() {
        return fetchCode;
    }

    public void setFetchCode(String fetchCode) {
        this.fetchCode = fetchCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }
}