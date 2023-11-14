package only.leo.wfm.core.dao;

import java.util.List;

import only.leo.wfm.common.beans.DirectoryDO;
import only.leo.wfm.common.beans.DirectoryDOExample;
import org.apache.ibatis.annotations.Param;

public interface DirectoryDOMapper {
    long countByExample(DirectoryDOExample example);

    int deleteByExample(DirectoryDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DirectoryDO record);

    int insertSelective(DirectoryDO record);

    List<DirectoryDO> selectByExample(DirectoryDOExample example);

    DirectoryDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DirectoryDO record, @Param("example") DirectoryDOExample example);

    int updateByExample(@Param("record") DirectoryDO record, @Param("example") DirectoryDOExample example);

    int updateByPrimaryKeySelective(DirectoryDO record);

    int updateByPrimaryKey(DirectoryDO record);
}