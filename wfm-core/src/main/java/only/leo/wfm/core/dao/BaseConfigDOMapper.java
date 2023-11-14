package only.leo.wfm.core.dao;

import java.util.List;
import only.leo.wfm.common.beans.BaseConfigDO;
import only.leo.wfm.common.beans.BaseConfigDOExample;
import org.apache.ibatis.annotations.Param;

public interface BaseConfigDOMapper {
    long countByExample(BaseConfigDOExample example);

    int deleteByExample(BaseConfigDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BaseConfigDO record);

    int insertSelective(BaseConfigDO record);

    List<BaseConfigDO> selectByExample(BaseConfigDOExample example);

    BaseConfigDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BaseConfigDO record, @Param("example") BaseConfigDOExample example);

    int updateByExample(@Param("record") BaseConfigDO record, @Param("example") BaseConfigDOExample example);

    int updateByPrimaryKeySelective(BaseConfigDO record);

    int updateByPrimaryKey(BaseConfigDO record);
}