package only.leo.wfm.core.service;

import only.leo.wfm.common.beans.Directory;
import only.leo.wfm.common.beans.DirectoryVO;
import only.leo.wfm.common.beans.IndexDirectory;
import only.leo.wfm.common.beans.IndexDirectoryVO;

import java.util.List;

public interface IDirectoryService {
    int insertRecord(Directory record);
    List<DirectoryVO> getList(Directory directory);

    /**
     * like match
     * @param path
     * @return
     */
    List<DirectoryVO> getListByPath(String key);
    void remove(Directory example);
    void removeByIds(List<Integer> ids);
    void rmRecursively(Directory example);
    void initCache();
    DirectoryVO queryById(int id);
    /**
     * cache query
     * @param id
     * @return
     */
    String queryPathById(int id);
    /**
     * cache query
     * @param path
     * @return
     */
    Integer queryIdByPath(String path);
}
