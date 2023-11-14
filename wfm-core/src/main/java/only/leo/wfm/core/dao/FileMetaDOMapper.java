package only.leo.wfm.core.dao;

import java.util.List;

import only.leo.wfm.common.beans.FileMetaDO;
import only.leo.wfm.common.beans.FileMetaDOExample;
import org.apache.ibatis.annotations.Param;

public interface FileMetaDOMapper {
    long countByExample(FileMetaDOExample example);

    int deleteByExample(FileMetaDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(FileMetaDO record);
    int batchInsert(List<FileMetaDO> list);
    Integer existByPrimaryKey(String id);
    int insertSelective(FileMetaDO record);

    List<FileMetaDO> selectByExample(FileMetaDOExample example);

    FileMetaDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") FileMetaDO record, @Param("example") FileMetaDOExample example);

    int updateByExample(@Param("record") FileMetaDO record, @Param("example") FileMetaDOExample example);

    int updateByPrimaryKeySelective(FileMetaDO record);

    int updateByPrimaryKey(FileMetaDO record);
}