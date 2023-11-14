package only.leo.wfm.core.service;

import only.leo.wfm.common.beans.IndexDirectoryVO;
import only.leo.wfm.core.runnable.idea.ProcessHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: LEO
 * @Date: 2021/9/2 8:59
 */
@Service
public class FileDetectedService {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private ProcessHandler processHandler;
    @Autowired
    private IndexService indexDirectoryService;
    private String[] watchPaths = new String[0];;

    public boolean isWatch(String path){
        for(String p:watchPaths){
            if(path.startsWith(p)) return true;
        }
        return false;
    }
    /**
     * start/restart listening file changes,
     */
    public void startIndexListen(){
        List<IndexDirectoryVO> indexDirectoryVOList = indexDirectoryService.getList();
        List<String> paths = new ArrayList<>();
        if(CollectionUtils.isEmpty(indexDirectoryVOList)){
            logger.warn("there is no IndexDirectory");
            watchPaths = new String[0];
            return;
        }
        for(IndexDirectoryVO indexDirectoryVO:indexDirectoryVOList){
            if (!indexDirectoryVO.getListen()) continue;
            paths.add(indexDirectoryVO.getPath());
        }
        try {
            processHandler.listenPath(paths);
        } catch (IOException e) {
            logger.error("error occurs when listenning index path", e);
        }
        logger.info("start listening all index path ");
        for(String path:paths){
            logger.info("path = "+path);
        }
        watchPaths = paths.toArray(new String[paths.size()]);
    }
    public String[] getWatchPaths(){
        return watchPaths;
    }
    public void restartIndexListen(){
        startIndexListen();
    }
}
