package only.leo.wfm.core.service;

import only.leo.wfm.common.beans.IndexDirectory;
import only.leo.wfm.common.beans.IndexDirectoryVO;

import java.util.List;

public interface IIndexService {
    int addRecord(IndexDirectory record);
    void reIndex(Short id) throws Exception;
    void index(String path,IndexDirectory indexDirectory) throws Exception;
    void addIndex(String path,String type,String authentication,boolean listen) throws Exception;
    List<IndexDirectoryVO> getList();

    /**
     * 获取location 所属索引
     * @param location
     * @return
     */
    IndexDirectoryVO queryByLocation(String location);
    void removeIndex(Short id);
    /**
     * cache search
     * @param id
     * @return
     */
    IndexDirectoryVO queryById(Short id);
    String queryAuth(Short id);
    int update(IndexDirectory example);
}
