package only.leo.wfm.web.boot.controller;

import only.leo.wfm.common.ResultBean;
import only.leo.wfm.common.beans.IndexDirectory;
import only.leo.wfm.common.beans.IndexDirectoryVO;
import only.leo.wfm.common.util.StringUtil;
import only.leo.wfm.core.service.FileDetectedService;
import only.leo.wfm.core.service.IIndexService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/index")
public class IndexController {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private IIndexService indexService;
    @Autowired
    private FileDetectedService fileDetectedService;
    @RequestMapping(path="/create",method = RequestMethod.POST)
    public ResultBean createIndex(String path,String type,String userName,String passwd,Boolean listen){
        String msg = ResultBean.MSG.OK;
        if(StringUtil.isEmpty(path)){
            return new ResultBean(ResultBean.MSG.ERROR,ResultBean.CODE.ERROR);
        }
        try {
            indexService.addIndex(path,type,userName+"/"+passwd,listen == null?false:listen);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ResultBean(e.getMessage(),ResultBean.CODE.ERROR);
        }
        return new ResultBean(msg,ResultBean.CODE.SUCCESS);
    }
    @RequestMapping(path="/update",method = RequestMethod.POST)
    public ResultBean updateIndex(IndexDirectory indexDirectory){
        String msg = ResultBean.MSG.OK;
        IndexDirectoryVO vo = indexService.queryById(indexDirectory.getId());
        indexService.update(indexDirectory);
        return new ResultBean(msg,ResultBean.CODE.SUCCESS);
    }
    @RequestMapping(path="/reIndex",method = RequestMethod.POST)
    public ResultBean reIndex(Short id){
        String msg = ResultBean.MSG.OK;
        try {
            indexService.reIndex(id);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            return new ResultBean(e.getMessage(),ResultBean.CODE.ERROR);
        }
        return new ResultBean(msg,ResultBean.CODE.SUCCESS);
    }
    @RequestMapping(path="/removeIndex",method = RequestMethod.POST)
    public ResultBean removeIndex(Short id){
        String msg = ResultBean.MSG.OK;
        indexService.removeIndex(id);
        return new ResultBean(msg,ResultBean.CODE.SUCCESS);
    }
    @RequestMapping(path="/getList",method = RequestMethod.GET)
    public ResultBean getList(){
        ResultBean resultBean = new ResultBean(ResultBean.MSG.OK,ResultBean.CODE.SUCCESS);
        List<IndexDirectoryVO> list = indexService.getList();
        resultBean.setBeans(list);
        return resultBean;
    }
}
