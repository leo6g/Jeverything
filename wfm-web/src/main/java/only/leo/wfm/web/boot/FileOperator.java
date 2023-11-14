package only.leo.wfm.web.boot;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import only.leo.wfm.common.ProtocolType;
import only.leo.wfm.common.beans.FileMetaVO;
import only.leo.wfm.common.exception.FileException;
import only.leo.wfm.common.io.InputStreamWrapper;
import only.leo.wfm.common.util.FileUtil;
import only.leo.wfm.core.service.IIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author: LIBAO
 */
@Component
public class FileOperator {
    @Autowired
    private IIndexService indexService;
    /**
     *
     * @param fileMetaVO
     * @param response
     * @return 文件流
     */
    public long fetchFile(FileMetaVO fileMetaVO, InputStreamWrapper in, HttpServletResponse response){
        String location = "";
        long fileLength = 0l;
        try {
            if(fileMetaVO.getRemote()){
                location = fileMetaVO.getPath()+fileMetaVO.getFileName();
                ProtocolType protocolType = FileUtil.getFileProtocolType(fileMetaVO.getPath());
                if(ProtocolType.SMB==protocolType){
                    String authStr = indexService.queryAuth(fileMetaVO.getIndexId());
                    String[] auth = authStr.split("/");
                    if(auth.length==2){
                        NtlmPasswordAuthentication authentication = new NtlmPasswordAuthentication(null, auth[0], auth[1]);
                        SmbFile file = new SmbFile(location,authentication);
                        if(!file.exists()) {
                            ResponseHandler.sendError("文件不存在！",response);
                            return -1;
                        }
                        fileLength = file.length();
                        in.setIn(file.getInputStream());
                    }else {
                        ResponseHandler.sendError("无效的smb认证信息！",response);
                    }
                }
            }else {
                location = fileMetaVO.getPath()+ File.separator+fileMetaVO.getFileName();
                File file = new File(location);
                if(!file.exists()) {
                    ResponseHandler.sendError("文件不存在！",response);
                    return -1;
                }
                fileLength = file.length();
                in.setIn(new FileInputStream(file));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLength;
    }

    public boolean deleteFile(FileMetaVO fileMetaVO, HttpServletResponse response) throws FileException{
        return true;
    }
}
