package only.leo.wfm.common;

/**
 * @Author: LEO
 * @Date: 2021/8/16 15:46
 */
public enum FileType {

    AUDIO("音频",(byte)1),VIDEO("视频",(byte)2),DOC("文档",(byte)3),IMAGE("图片",(byte)4),TEXT("文本",(byte)5),REGULAR("-",(byte)6)
    ,ARCHIVE("压缩包",(byte)7),DIRECTORY("文件夹",(byte)8),NO_INDEX("其它",(byte)9),EXE("可执行",(byte)10),OTHER("其它",(byte)11);
    private String name;
    private byte code;
    FileType(String name,byte code) {
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
}
