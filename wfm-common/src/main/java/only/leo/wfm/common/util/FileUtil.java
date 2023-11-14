package only.leo.wfm.common.util;

import only.leo.wfm.common.Constants;
import only.leo.wfm.common.FileType;
import only.leo.wfm.common.ProtocolType;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author: LEO
 * @Date: 2021/8/20 14:41
 */
public class FileUtil {
    //if file is present already in given path,rename it with a increase number
    public static String getAvaliableUploadPath(Path path,String fileName,String originName,int begin){
        if(StringUtil.isEmpty(fileName)||begin>100) return null;
        File file = path.resolve(fileName).toFile();
        if(file.exists()){
            StringBuilder sb = new StringBuilder(originName);
            int index = sb.lastIndexOf(".");
            if(index==-1) index = sb.length();
            begin++;
            sb.insert(index," ("+begin+")");
            return getAvaliableUploadPath(path,sb.toString(),fileName,begin++);
        }else {
            return fileName;
        }
    }

    public static String getExtensionName(String fileName){
        int index = fileName.lastIndexOf(".");
        if(index!=-1){
            return fileName.substring(index+1).toLowerCase();
        }
        return Constants.NONE;
    }
    public static ProtocolType getFileProtocolType(String filePath){
        if(filePath.startsWith("smb:")){
            return ProtocolType.SMB;
        }else if(filePath.startsWith("ftp:")){
            return ProtocolType.FTP;
        }else if(filePath.startsWith("http:")){
            return ProtocolType.HTTP;
        }else {
            //暂默认为local
            return ProtocolType.LOCAL;
        }
    }
    public static FileType getFileType(String fileName){
        FileType fileType = Constants.fileTypeMap.get(getExtensionName(fileName));
        return fileType==null?FileType.NO_INDEX:fileType;
    }
    public static FileType getFileType(File file){
        if(file.isDirectory()) return FileType.DIRECTORY;
        FileType fileType = Constants.fileTypeMap.get(getExtensionName(file.getName()));
        return fileType==null?FileType.NO_INDEX:fileType;
    }
    public static byte getFileCode(String name){
        Byte code = null;
        switch (name){
            case "音频":
                code = FileType.AUDIO.getCode();
                break;
            case "视频":
                code = FileType.VIDEO.getCode();
                break;
            case "文档":
                code = FileType.DOC.getCode();
                break;
            case "图片":
                code = FileType.IMAGE.getCode();
                break;
            case "文本":
                code = FileType.TEXT.getCode();
                break;
            case "常规":
                code = FileType.REGULAR.getCode();
                break;
            case "压缩包":
                code = FileType.ARCHIVE.getCode();
                break;
            case "文件夹":
                code = FileType.DIRECTORY.getCode();
                break;
            case "可执行":
                code = FileType.EXE.getCode();
                break;
            case "其它":
                code = FileType.OTHER.getCode();
                break;
            default:
                code = FileType.NO_INDEX.getCode();
        }
        return code;
    }

    public static FileType getFileType(int code){
        FileType fileType = null;
        switch (code){
            case 1:
                fileType = FileType.AUDIO;
                break;
            case 2:
                fileType = FileType.VIDEO;
                break;
            case 3:
                fileType = FileType.DOC;
                break;
            case 4:
                fileType = FileType.IMAGE;
                break;
            case 5:
                fileType = FileType.TEXT;
                break;
            case 6:
                fileType = FileType.REGULAR;
                break;
            case 7:
                fileType = FileType.ARCHIVE;
                break;
            case 8:
                fileType = FileType.DIRECTORY;
                break;
            case 10:
                fileType = FileType.EXE;
                break;
                //编程文件暂不扫描
          /*  case 11:
                fileType = FileType.OTHER;
                break;*/
            default:
                fileType = FileType.NO_INDEX;
        }
        return fileType;
    }
    public static ProtocolType getProtocolType(int code){
        ProtocolType protocolType = null;
        switch (code){
            case 1:
                protocolType = ProtocolType.LOCAL;
                break;
            case 2:
                protocolType = ProtocolType.SMB;
                break;
            case 3:
                protocolType = ProtocolType.FTP;
                break;
            case 4:
                protocolType = ProtocolType.HTTP;
                break;
            default:
                protocolType = ProtocolType.UNSUPPORTED;
        }
        return protocolType;
    }
}
