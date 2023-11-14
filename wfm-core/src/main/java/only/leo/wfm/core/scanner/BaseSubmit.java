package only.leo.wfm.core.scanner;

import only.leo.wfm.common.FileSinkOperation;
import only.leo.wfm.common.ProtocolType;
import only.leo.wfm.common.beans.FileEvent;
import only.leo.wfm.common.beans.FileMeta;
import only.leo.wfm.common.beans.FileMetaVO;
import only.leo.wfm.common.beans.IndexDirectory;
import only.leo.wfm.core.Pipeline;
import only.leo.wfm.core.service.FileDetectedService;
import only.leo.wfm.core.service.IDirectoryService;
import only.leo.wfm.core.service.IFileManagerService;

import java.util.HashSet;
import java.util.List;

/**
 * @Author: LEO
 * @Date: 2021/10/18 15:53
 */
public abstract class BaseSubmit<T> {
    public BaseSubmit(List<T> submitFiles, IndexDirectory indexDirectory, Pipeline<FileEvent> pipeline, FileDetectedService fileDetectedService
            , IDirectoryService directoryService, IFileManagerService fileManagerService) {
        this.submitFiles = submitFiles;
        this.indexDirectory = indexDirectory;
        this.pipeline = pipeline;
        this.fileDetectedService = fileDetectedService;
        this.directoryService = directoryService;
        this.fileManagerService = fileManagerService;
    }
    HashSet<String> fileSignatures = new HashSet<>();
    List<T> submitFiles;
    IndexDirectory indexDirectory;
    Pipeline<FileEvent> pipeline;
    FileDetectedService fileDetectedService;
    IFileManagerService fileManagerService;
    IDirectoryService directoryService;

    abstract FileMeta toFileMeta(T file);
    void loadSignatures(String directoryPath){
        Integer directoryId = directoryService.queryIdByPath(directoryPath);
        if(directoryId==null) return;
        List<FileMetaVO> fileMetaVOList = fileManagerService.queryByDirectoryId(directoryId);
        for(FileMetaVO record:fileMetaVOList){
            fileSignatures.add(record.getId());
        }
    }

    /**
     * compare with records in DB to reduce db operations
     */
    void compareAndSubmit(){
        for(T file:submitFiles){
            FileMeta fileMeta = toFileMeta(file);
            if(fileSignatures.contains(fileMeta.getSignature())){
                fileSignatures.remove(fileMeta.getSignature());
            }else{
                submit(fileMeta, FileSinkOperation.ADD);
            }
        }
        for(String toRemove:fileSignatures){
            FileMeta fileMeta = new FileMeta();
            fileMeta.setSignature(toRemove);
            fileMeta.setDirectory(false);
            submit(fileMeta, FileSinkOperation.REMOVE);
        }
    }

    private void submit(FileMeta fileMeta, FileSinkOperation operation){
        fileMeta.setRemote(indexDirectory.getRemote());
        fileMeta.setProtocolType(ProtocolType.fromCode(indexDirectory.getProtocol()));
        fileMeta.setIndexId(indexDirectory.getId());
        //cant detected folders of remote
        this.pipeline.submit(new FileEvent(fileMeta, operation));
    }
}
