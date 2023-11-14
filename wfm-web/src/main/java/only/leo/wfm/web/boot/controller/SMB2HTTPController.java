package only.leo.wfm.web.boot.controller;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import only.leo.wfm.common.util.FileUtil;
import only.leo.wfm.common.util.StringUtil;
import only.leo.wfm.common.Constants;
import only.leo.wfm.common.FileType;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author: LEO
 * @Date: 2021/8/18 9:10
 */
@Controller
public class SMB2HTTPController {
    @Value("${smbHost}")
    private String smbHost;
    @Value("${smbUserName}")
    private String smbUserName;
    @Value("${smbPass}")
    private String smbPass;
    @RequestMapping("/smb/**")
    public void smb2http(HttpServletRequest request, HttpServletResponse response){
        String uri = request.getRequestURI();
        NtlmPasswordAuthentication authentication = new NtlmPasswordAuthentication(null, "LEO", "0000");
        String smbLocation = "smb://"+smbHost+"/"+uri.replace("/smb/","");
        InputStream in = null;
        String rangeStr = request.getHeader("Range");
        long rangeBegin = 0l;
        if(StringUtil.isNotEmpty(rangeStr)){
            rangeBegin = Long.valueOf(rangeStr.replace("bytes=","").replace("-",""));
        }
        try {
            SmbFile file = new SmbFile(smbLocation,authentication);
            System.out.println(file.getName());
            String ext = FileUtil.getExtensionName(uri);
            String contentType = Constants.contentTypeMap.get(ext);
            FileType fileType = Constants.fileTypeMap.get(ext);
            int length = file.getContentLength();
            System.out.println(length);
            response.setContentLength(length);
            response.setContentType(contentType);
            if(fileType==FileType.AUDIO||fileType==FileType.VIDEO){
                System.out.println(rangeBegin);
                response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
                response.addHeader("Content-Range","bytes "+rangeBegin+"-"+(length-1)+"/"+length);
                response.addHeader("Connection","keep-alive");
                response.addHeader("Cache-Control","max-age=31536000");
            }
            OutputStream out = response.getOutputStream();
            in = file.getInputStream();
            in.skip(rangeBegin);
            IOUtils.copyAndCloseInput(in,out);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                if(in!=null)in.close();
            } catch (IOException e) {
            }
        }
    }

}
