package only.leo.wfm.web.boot.controller;

import only.leo.wfm.common.*;
import only.leo.wfm.common.beans.FileMetaVO;
import only.leo.wfm.common.beans.ShareFile;
import only.leo.wfm.common.io.IOUtil;
import only.leo.wfm.common.io.InputStreamWrapper;
import only.leo.wfm.common.io.StreamRange;
import only.leo.wfm.common.util.FileUtil;
import only.leo.wfm.web.boot.FileOperator;
import only.leo.wfm.web.boot.ResponseHandler;
import only.leo.wfm.common.util.StringUtil;
import only.leo.wfm.common.util.SystemUtil;
import only.leo.wfm.core.service.IFileManagerService;
import only.leo.wfm.core.service.IIndexService;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.SizeLimitExceededException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileManagerController {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private IFileManagerService fileManagerService;
    @Autowired
    private FileOperator fileOperator;
    @RequestMapping(path="/search",method = RequestMethod.GET)
    public ResultBean search(String key,Integer top,String fileType){
        ResultBean resultBean = new ResultBean(ResultBean.MSG.OK,ResultBean.CODE.SUCCESS);
        if(StringUtil.isEmpty(key)) return resultBean;
        List<FileMetaVO> list = fileManagerService.search(key,top==null?10:top,fileType);
        resultBean.setBeans(list);
        return resultBean;
    }

    @RequestMapping(path="/recent",method = RequestMethod.GET)
    public ResultBean recent(Integer top,String fileType){
        ResultBean resultBean = new ResultBean(ResultBean.MSG.OK,ResultBean.CODE.SUCCESS);
        List<FileMetaVO> list = fileManagerService.queryRecent(top==null?10:top,fileType);
        resultBean.setBeans(list);
        return resultBean;
    }
    @RequestMapping(path="/quickSearch",method = RequestMethod.GET)
    public ResultBean quickSearch(String key,String type,int top,String fileType){
        ResultBean resultBean = new ResultBean(ResultBean.MSG.OK,ResultBean.CODE.SUCCESS);
        if(StringUtil.isEmpty(key))return resultBean;
        List<FileMetaVO> list = fileManagerService.search(key,top,fileType);
        resultBean.setBeans(list);
        return resultBean;
    }

    @RequestMapping(path="/openFile",method = RequestMethod.GET)
    public void openFile(String path){
        if(StringUtil.isEmpty(path)||PlatformSystem.WINDOWS!=SystemUtil.getPlatformSystem()) return;
        SystemUtil.openFile(path);
    }
    @RequestMapping(path="/openDIR",method = RequestMethod.GET)
    public void openDIR(String path){
        if(StringUtil.isEmpty(path)||PlatformSystem.WINDOWS!=SystemUtil.getPlatformSystem()) return;
        SystemUtil.openDIR(path);
    }


    private void sendVerifyForm(String fileName,HttpServletResponse response){
        try {
            response.setContentType("text/html;charset=utf-8");
            String html = "<head>\n" +
                    "    <title>请输入提取码</title>\n" +
                    "</head>\n" +
                    "<body style=\"text-align: center;padding: 200px;\">\n" +
                    "    <p>正在提取：<b><i>"+fileName+"</i></b>&nbsp;&nbsp;请输入提取码...</p>\n" +
                    "    <input id=\"fetchCode\" type=\"text\"></input>&nbsp;&nbsp;<button type=\"submit\" onclick=\"fetch()\">提取</button>\n" +
                    "    <script>\n" +
                    "        function fetch(){\n" +
                    "            window.location.href = window.location+\"&fetchCode=\"+document.getElementById(\"fetchCode\").value;\n" +
                    "        }\n" +
                    "    </script>\n" +
                    "</body>";
            response.getWriter().print(html);
            response.getWriter().close();
        } catch (IOException e) {
        }
    }
    @RequestMapping(path="/download",method = RequestMethod.GET)
    public void publicDownload(String ticket,String fetchCode,HttpServletRequest request,HttpServletResponse response){
        if(StringUtil.isEmpty(ticket)) {
            ResponseHandler.sendError("ticket is empty",response);
            return;
        }
        ticket = ticket.trim();
        if(!StringUtil.isEmpty(fetchCode))fetchCode = fetchCode.trim();
        if(MemoryCache.cacheSharedFile.containsKey(ticket)){
            ShareFile shareFile = MemoryCache.cacheSharedFile.get(ticket);

            if(shareFile.getFetchCode()==null||(shareFile.getFetchCode()!=null&&fetchCode!=null&&shareFile.getFetchCode().equalsIgnoreCase(fetchCode))){
                downloadFile(ticket,request,response);
            }else if(fetchCode==null){
                sendVerifyForm(shareFile.getFileName(),response);
            }else if(!shareFile.getFetchCode().equalsIgnoreCase(fetchCode)){
                ResponseHandler.sendError("提取码错误！",response);
            }
        }else {
            ResponseHandler.sendError("分享已失效！请联系管理员。",response);
        }
    }

    @RequestMapping(path="/downloadFile",method = RequestMethod.GET)
    public void downloadFile(String ticket,HttpServletRequest request, HttpServletResponse response){
        FileMetaVO fileMetaVO = fileManagerService.queryById(ticket);
        if(fileMetaVO==null) {
            ResponseHandler.sendError("文件不可用！",response);
            return;
        }
        OutputStream out = null;
        String rangeStr = request.getHeader("Range");
        StreamRange streamRange = null;
        if(StringUtil.isNotEmpty(rangeStr))
            streamRange = StreamRange.resolveRange0(rangeStr);
        InputStreamWrapper in = new InputStreamWrapper();
        try {
            long fileLength = fileOperator.fetchFile(fileMetaVO,in,response);
            if(fileLength==-1) return;
            if(streamRange==null){
                response.setContentLengthLong(fileLength);
            }else {
                response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
                response.setContentLengthLong(streamRange.isFromStart()?fileLength-streamRange.getStartPos():streamRange.getLength());
            }
            response.setContentType("application/octet-stream;charset=UTF-8");
            if(streamRange!=null)
                response.addHeader("Content-Range",makeContentRangeStr(streamRange,fileLength));
            response.setHeader("Cache-Control","max-age=18000");
            response.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(fileMetaVO.getFileName(), "UTF-8"));
            out = response.getOutputStream();
            if(streamRange!=null)
                in.getIn().skip(streamRange.getStartPos());
            if(streamRange!=null&&!streamRange.isToEnd()&&!streamRange.isFromStart()){
                IOUtil.copyAndCloseInput(in.getIn(),out,streamRange.getLength());
            }else {
                IOUtil.copyAndCloseInput(in.getIn(),out);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if(in.getIn()!=null)in.getIn().close();
                if(out!=null)out.close();
            } catch (IOException e) {
            }
        }
    }



    private String makeContentRangeStr(StreamRange streamRange,long fileLength){
        String template = "bytes %s-%s/%s";
        String result = "";
        if(streamRange.isFromStart()){
            result = String.format(template,streamRange.getStartPos(),fileLength-1,fileLength);
        }else if(streamRange.isToEnd()){
            result = String.format(template,fileLength-streamRange.getEndPos(),fileLength-1,fileLength);
        }else {
            result = String.format(template,streamRange.getStartPos(),streamRange.getEndPos(),fileLength);
        }
        return result;
    }
    @RequestMapping(path="/stream",method = RequestMethod.GET)
    public void stream(String id,String fetchCode, HttpServletRequest request, HttpServletResponse response){
        if(StringUtil.isEmpty(id)) {
            ResponseHandler.sendError("id is empty",response);
            return;
        }
        id = id.trim();
        if(!StringUtil.isEmpty(fetchCode))fetchCode = fetchCode.trim();
        if(MemoryCache.cacheSharedFile.containsKey(id)){
            ShareFile shareFile = MemoryCache.cacheSharedFile.get(id);
            if(shareFile.getFetchCode()==null||(shareFile.getFetchCode()!=null&&fetchCode!=null&&shareFile.getFetchCode().equalsIgnoreCase(fetchCode))){
                viewFile(id,request,response);
            }else if(fetchCode==null){
                sendVerifyForm(shareFile.getFileName(),response);
            }else if(!shareFile.getFetchCode().equalsIgnoreCase(fetchCode)){
                ResponseHandler.sendError("提取码错误！",response);
            }
        }else {
            ResponseHandler.sendError("推流已失效！请联系管理员。",response);
        }
    }
    @PostMapping("/upload")
    public ResultBean handleFileUpload(@RequestParam("file") MultipartFile file) {
        ResultBean resultBean = new ResultBean(ResultBean.MSG.OK,ResultBean.CODE.SUCCESS);
        if (file.isEmpty()) {
            resultBean.setMsg(ResultBean.MSG.ERROR);
            resultBean.setCode(ResultBean.CODE.ERROR);
            return resultBean;
        }
        try {
            String currentWorkingDirectory = System.getProperty("user.dir");
            Path uploadHomePath = Paths.get(currentWorkingDirectory,"upload");
            File uploadHomeFile = uploadHomePath.toFile();
            if(!uploadHomeFile.exists()) {
                uploadHomeFile.mkdirs();
            }
            String fileName = FileUtil.getAvaliableUploadPath(uploadHomePath,file.getOriginalFilename(),file.getOriginalFilename(),0 );
            Path uploadPath = uploadHomePath.resolve(fileName);
            file.transferTo(uploadPath.toFile());
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            resultBean.setMsg(ResultBean.MSG.ERROR);
            resultBean.setCode(ResultBean.CODE.ERROR);
        }
        return resultBean;
    }

    @RequestMapping(path="/viewFile",method = RequestMethod.GET)
    public void viewFile(String id, HttpServletRequest request, HttpServletResponse response){
        FileMetaVO fileMetaVO = fileManagerService.queryById(id);
        if(fileMetaVO==null) {
            ResponseHandler.sendError("文件不可用！",response);
            return;
        }
        String rangeStr = request.getHeader("Range");
        StreamRange streamRange = StreamRange.resolveRange0(rangeStr);
        OutputStream out = null;
        InputStreamWrapper in = new InputStreamWrapper();
        try {
            long fileLength = fileOperator.fetchFile(fileMetaVO,in,response);
            if(fileLength==-1) return;
            String ext = FileUtil.getExtensionName(fileMetaVO.getFileName());
            String contentType = Constants.contentTypeMap.get(ext);
            FileType fileType = Constants.fileTypeMap.get(ext);
            response.setContentType(contentType);
            if(fileType==FileType.AUDIO||fileType==FileType.VIDEO){
                response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
                response.addHeader("Content-Range",makeContentRangeStr(streamRange,fileLength));
                response.addHeader("Connection","keep-alive");
                response.addHeader("Cache-Control","max-age=18000");
            }
            out = response.getOutputStream();
            in.getIn().skip(streamRange.getStartPos());
            IOUtil.copyAndCloseInput(in.getIn(),out);
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if(in.getIn()!=null)in.getIn().close();
                if(out!=null)out.close();
            } catch (IOException e) {
            }
        }
    }
    @RequestMapping(path="/delete/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        FileMetaVO fileMetaVO = fileManagerService.queryById(id);
        if(fileMetaVO==null) {
            ResponseHandler.sendError("文件不可用！",response);
            return;
        }
        try {
            boolean success = fileOperator.deleteFile(fileMetaVO, response);
        } finally {

        }
    }
}
