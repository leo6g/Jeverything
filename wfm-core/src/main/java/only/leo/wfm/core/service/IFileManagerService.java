package only.leo.wfm.core.service;


import only.leo.wfm.common.beans.FileMeta;
import only.leo.wfm.common.beans.FileMetaDO;
import only.leo.wfm.common.beans.FileMetaDOExample;
import only.leo.wfm.common.beans.FileMetaVO;

import java.util.List;

public interface IFileManagerService {
    List<FileMetaVO> search(String key, int top,String fileType);
    void add(FileMeta record,boolean immediately);
    void addNX(FileMeta record,boolean immediately);
    void removeById(String id);
    void removeByDir(Integer dirId);
    void removeByDirs(List<Integer> dirIds);
    void removeByIndex(Short indexId);
    List<FileMetaVO> queryRecent(int top,String fileType);
    void update(FileMeta example);
    FileMetaVO queryById(String id);
    List<FileMetaVO> queryByDirectoryId(Integer directoryId);
    long countByIndexId(short id);
}
