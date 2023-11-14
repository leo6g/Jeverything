package only.leo.wfm.common.beans;

import java.util.Date;

public class ShareFileDO {
    private String code;

    private String fileName;

    private String fetchCode;

    private Integer downloadCount;

    private Boolean enable;

    private Date shareTime;

    private Date invalidTime;

    public ShareFileVO toVO(){
        ShareFileVO shareFileVO = new ShareFileVO();
        shareFileVO.setCode(code);
        shareFileVO.setEnable(enable);
        shareFileVO.setShareTime(shareTime);
        shareFileVO.setInvalidTime(invalidTime);
        shareFileVO.setFetchCode(fetchCode);
        shareFileVO.setFileName(fileName);
        return shareFileVO;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFetchCode() {
        return fetchCode;
    }

    public void setFetchCode(String fetchCode) {
        this.fetchCode = fetchCode == null ? null : fetchCode.trim();
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
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