package only.leo.wfm.core.scanner;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import only.leo.wfm.common.FileType;
import only.leo.wfm.common.beans.IndexDirectory;
import only.leo.wfm.common.util.FileUtil;
import only.leo.wfm.core.Pipeline;
import only.leo.wfm.core.ScanFilesException;
import only.leo.wfm.core.service.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * scan remote resource in single thread
 * @Author: LEO
 * @Date: 2021/8/16 15:31
 */
public class SMBFileScanner extends AbstractScanner<SmbFile>{
    private static Logger logger = Logger.getLogger(SMBFileScanner.class);
    private NtlmPasswordAuthentication authentication;
    private int currentDeep = 0;
    public SMBFileScanner(String scanPath, IndexDirectory indexDirectory, Pipeline pipeline, FileDetectedService fileDetectedService
            , IDirectoryService directoryService, IFileManagerService fileManagerService) {
        this.scanPath = scanPath.endsWith("/")?scanPath:scanPath+"/";
        this.indexDirectory = indexDirectory;
        this.fileDetectedService = fileDetectedService;
        this.pipeline = pipeline;
        this.directoryService = directoryService;
        this.fileManagerService = fileManagerService;
        String userName = indexDirectory.getAuthentication().split("/")[0];
        String pass = indexDirectory.getAuthentication().split("/")[1];
        this.authentication = new NtlmPasswordAuthentication(null, userName, pass);
    }

    @Override
    void startInner() {
        try {
            SmbFile file = new SmbFile(scanPath,authentication);
            scan(file);
        } catch (ScanFilesException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 主扫描
     * @param currentDir
     * @throws ScanFilesException
     */
    protected void scan(SmbFile currentDir) throws ScanFilesException {
        currentDeep++;
        if(currentDeep>maxDeeps) return;
        try {
            if(!currentDir.isDirectory()){
                throw new ScanFilesException('"' + currentDir.getPath() + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
            }
            boolean f = false;
            SmbFile [] fileList = currentDir.listFiles();
            if(fileList==null) return;
            List<SmbFile> submitFiles = new ArrayList<>();
            submitFiles.add(currentDir);
            for(int i = 0; i < fileList.length; i ++){
                SmbFile file = fileList[i];
                if(file==null||!file.exists()||file.isHidden()||(!file.isDirectory()&&FileUtil.getFileType(file.getName())==FileType.NO_INDEX)) continue;
                if(file.isDirectory()&&filter(file)){
                    //递归
                    scan(file);
                    currentDeep--;
                } else {
                    submitFiles.add(file);
                    f = true;
                }
            }
            if(f) {
                submit(submitFiles);
            }
        } catch (SmbException e) {
            e.printStackTrace();
        }
    }

    @Override
    void submit(List<SmbFile> files) {
        //重新扫描文件夹 提交文件夹和文件
        try {
            preSinkWorker.submit(new SubmitSMBFileRunner(files,indexDirectory,pipeline,fileDetectedService,directoryService,fileManagerService));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    boolean filter(SmbFile file) {
        return true;
    }
}
