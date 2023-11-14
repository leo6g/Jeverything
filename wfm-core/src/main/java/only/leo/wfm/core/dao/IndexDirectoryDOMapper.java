package only.leo.wfm.core.dao;

import java.util.List;

import only.leo.wfm.common.beans.IndexDirectoryDO;
import only.leo.wfm.common.beans.IndexDirectoryDOExample;
import org.apache.ibatis.annotations.Param;

public interface IndexDirectoryDOMapper {
    long countByExample(IndexDirectoryDOExample example);

    int deleteByExample(IndexDirectoryDOExample example);

    int deleteByPrimaryKey(Short id);

    int insert(IndexDirectoryDO record);

    int insertSelective(IndexDirectoryDO record);

    List<IndexDirectoryDO> selectByExample(IndexDirectoryDOExample example);

    IndexDirectoryDO selectByPrimaryKey(Short id);

    int updateByExampleSelective(@Param("record") IndexDirectoryDO record, @Param("example") IndexDirectoryDOExample example);

    int updateByExample(@Param("record") IndexDirectoryDO record, @Param("example") IndexDirectoryDOExample example);

    int updateByPrimaryKeySelective(IndexDirectoryDO record);

    int updateByPrimaryKey(IndexDirectoryDO record);
}