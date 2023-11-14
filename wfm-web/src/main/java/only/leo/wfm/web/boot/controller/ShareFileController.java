package only.leo.wfm.web.boot.controller;

import only.leo.wfm.common.MemoryCache;
import only.leo.wfm.common.ResultBean;
import only.leo.wfm.common.beans.FileMetaVO;
import only.leo.wfm.common.beans.ShareFile;
import only.leo.wfm.common.util.StringUtil;
import only.leo.wfm.common.util.UUIDGenerator;
import only.leo.wfm.core.service.IFileManagerService;
import only.leo.wfm.core.service.IShareFileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/share")
public class ShareFileController {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private IShareFileService shareFileService;
    @Autowired
    private IFileManagerService fileManagerService;

    /**
     * 暂不持久化分享信息，重启所有分享失效
     * @param code
     * @param accessControl
     * @return
     */
    @RequestMapping(path="/shareFile",method = RequestMethod.GET)
    public ResultBean shareFile(String fileId,Boolean accessControl){
        if(StringUtil.isEmpty(fileId)) new ResultBean("ID 为空",ResultBean.CODE.ERROR);
        FileMetaVO fileMetaVO = fileManagerService.queryById(fileId);
        if(fileMetaVO==null) new ResultBean("文件不可用！",ResultBean.CODE.ERROR);
        ShareFile shareFile = null;
        if(MemoryCache.cacheSharedFile.containsKey(fileId)){
            shareFile = MemoryCache.cacheSharedFile.get(fileId);
            if(accessControl&&shareFile.getFetchCode()==null) shareFile.setFetchCode(UUIDGenerator.generate().substring(0,6));
            if(!accessControl&&shareFile.getFetchCode()!=null) shareFile.setFetchCode(null);
        }else {
            shareFile = new ShareFile();
            shareFile.setCode(fileId);
            shareFile.setFileName(fileMetaVO.getFileName());
            if(accessControl)shareFile.setFetchCode(UUIDGenerator.generate().substring(0,6));
        }
        MemoryCache.cacheSharedFile.put(fileId,shareFile);
        ResultBean resultBean = new ResultBean(ResultBean.MSG.OK,ResultBean.CODE.SUCCESS);
        resultBean.setBean(shareFile);
        return resultBean;
    }
}
