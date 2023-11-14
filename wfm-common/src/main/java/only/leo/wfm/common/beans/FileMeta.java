package only.leo.wfm.common.beans;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import only.leo.wfm.common.FileType;
import only.leo.wfm.common.ProtocolType;
import only.leo.wfm.common.util.FileUtil;
import only.leo.wfm.common.util.MD5Util;

import java.io.File;
import java.util.Date;

/**
 * @Author: LEO
 * @Date: 2021/8/16 15:44
 */
public class FileMeta {
    private String extension;
    private String signature;
    private FileType fileType;
    private Boolean isDirectory;
    private String path;
    private String location;
    private String fileName;
    private Long fileSize;
    private Boolean isRemote;
    private ProtocolType protocolType;
    private Date lastModifyTime;
    private short indexId;

    public FileMeta(FileType fileType, String location) {
        this.fileType = fileType;
        this.isDirectory = fileType == FileType.DIRECTORY;
        this.location = location;
        this.signature = MD5Util.MD5(location);
    }

    public FileMeta() {
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public short getIndexId() {
        return indexId;
    }

    public void setIndexId(short indexId) {
        this.indexId = indexId;
    }

    public Boolean getRemote() {
        return isRemote;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRemote(Boolean remote) {
        isRemote = remote;
    }

    public ProtocolType getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Boolean getDirectory() {
        return isDirectory;
    }

    public void setDirectory(Boolean directory) {
        isDirectory = directory;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static FileMeta fromLocalFile(File file){
        FileMeta fileMeta = new FileMeta();
        fileMeta.setFileName(file.getName());
        fileMeta.setLastModifyTime(new Date(file.lastModified()));
        fileMeta.setDirectory(file.isDirectory());
        fileMeta.setPath(file.getParent());
        fileMeta.setLocation(file.getAbsolutePath());
        fileMeta.setFileSize(file.length());
        fileMeta.setSignature(MD5Util.MD5(fileMeta.getLocation()));
        //TODO 如果文件不存在 区别是文件 还是文件夹
        fileMeta.setFileType(fileMeta.getDirectory()?FileType.DIRECTORY:FileUtil.getFileType(fileMeta.getFileName()));
        return fileMeta;
    }
    public static FileMeta fromSMBFile(SmbFile file){
        FileMeta fileMeta = new FileMeta();
        try {
            fileMeta.setFileName(file.getName());
            fileMeta.setLastModifyTime(new Date(file.lastModified()));
            fileMeta.setDirectory(file.isDirectory());
            fileMeta.setPath(file.getParent());
            fileMeta.setLocation(file.getCanonicalPath());
            fileMeta.setFileSize(file.length());
            fileMeta.setSignature(MD5Util.MD5(fileMeta.getLocation()));
        } catch (SmbException e) {
            e.printStackTrace();
        }
        //TODO 如果文件不存在 区别是文件 还是文件夹
        fileMeta.setFileType(fileMeta.getDirectory()?FileType.DIRECTORY:FileUtil.getFileType(fileMeta.getFileName()));
        return fileMeta;
    }

    public FileMetaDO toDO(){
        FileMetaDO fileMetaDO = new FileMetaDO();
        fileMetaDO.setFileModifyTime(lastModifyTime);
        fileMetaDO.setFileName(fileName);
        fileMetaDO.setFileSize(fileSize);
        fileMetaDO.setFileType(isDirectory?FileType.DIRECTORY.getCode():fileType.getCode());
        fileMetaDO.setIndexId(indexId);
        fileMetaDO.setId(signature);
        return fileMetaDO;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "FileMeta{" +
                "extension='" + extension + '\'' +
                ", fileType=" + fileType +
                ", isDirectory=" + isDirectory +
                ", path='" + path + '\'' +
                ", fileName='" + fileName + '\'' +
                ", lastModifyTime=" + lastModifyTime +
                '}';
    }
}
