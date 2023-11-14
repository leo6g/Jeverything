package only.leo.wfm.core.runnable;
import only.leo.wfm.common.FileSinkOperation;
import only.leo.wfm.common.beans.Directory;
import only.leo.wfm.common.beans.FileEvent;
import only.leo.wfm.common.beans.FileMeta;
import only.leo.wfm.core.Pipeline;
import only.leo.wfm.core.service.DirectoryService;
import only.leo.wfm.core.service.FileManagerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;


/**
 * @Author: LEO
 * @Date: 2021/8/16 17:38
 */
@Component
public class H2Sink implements Runnable{
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private Pipeline<FileEvent> pipeline;
    @Autowired
    private FileManagerService fileManagerService;
    @Autowired
    private DirectoryService directoryService;
    public void run() {
        while (true){
            String location = "";
            try {
                FileEvent fileEvent = pipeline.take();
                FileSinkOperation operation = fileEvent.getFileSinkOperation();
                FileMeta fileMeta = fileEvent.getFileMeta();
                location = fileMeta.getLocation();
                boolean isDirectory = fileMeta.getDirectory();
                switch (operation){
                    case ADD:
                        if(isDirectory){
                            directoryService.insertRecord(new Directory(fileMeta.getIndexId(),fileMeta.getLocation(),fileMeta.getLastModifyTime()));
                        }else {
                            fileManagerService.addNX(fileMeta,pipeline.isEmpty());
                        }
                        break;
                    case REMOVE:
                        if(isDirectory){
                            directoryService.rmRecursively(new Directory(fileMeta.getLocation()));
                        }else {
                            fileManagerService.removeById(fileMeta.getSignature());
                        }
                        break;
                    case UPDATE:
                        //暂不处理文件夹修改事件，可在网盘模式中处理
                        if(!isDirectory)
                            fileManagerService.update(fileMeta);
                        break;
                }
            } catch (DuplicateKeyException e) {
                logger.warn(String.format("%s has already indexed,skips it",location));
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }catch (Error error) {
                logger.error(error.getMessage(),error);
            }
        }
    }
}
