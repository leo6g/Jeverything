package only.leo.wfm.common;

/**
 * @Author: LEO
 */
public enum ProtocolType {

    LOCAL("LOCAL",(byte)1),SMB("SMB",(byte)2),FTP("FTP",(byte)3),HTTP("HTTP",(byte)4),UNSUPPORTED("UNSUPPORTED",(byte)5);
    private String name;
    private byte code;
    ProtocolType(String name, byte code) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public static ProtocolType fromName(String name){
        switch (name){
            case "LOCAL":
                return ProtocolType.LOCAL;
            case "SMB":
                return ProtocolType.SMB;
            case "FTP":
                return ProtocolType.FTP;
            case "HTTP":
                return ProtocolType.HTTP;
            default:
                return ProtocolType.UNSUPPORTED;
        }
    }
    public static ProtocolType fromCode(byte code){
        switch (code){
            case 1:
                return ProtocolType.LOCAL;
            case 2:
                return ProtocolType.SMB;
            case 3:
                return ProtocolType.FTP;
            case 4:
                return ProtocolType.HTTP;
            default:
                return ProtocolType.UNSUPPORTED;
        }
    }
}
