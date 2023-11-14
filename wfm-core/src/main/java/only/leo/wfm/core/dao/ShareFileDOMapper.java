package only.leo.wfm.core.dao;

import java.util.List;
import only.leo.wfm.common.beans.ShareFileDO;
import only.leo.wfm.common.beans.ShareFileDOExample;
import org.apache.ibatis.annotations.Param;

public interface ShareFileDOMapper {
    long countByExample(ShareFileDOExample example);

    int deleteByExample(ShareFileDOExample example);

    int deleteByPrimaryKey(String code);

    int insert(ShareFileDO record);

    int insertSelective(ShareFileDO record);

    List<ShareFileDO> selectByExample(ShareFileDOExample example);

    ShareFileDO selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") ShareFileDO record, @Param("example") ShareFileDOExample example);

    int updateByExample(@Param("record") ShareFileDO record, @Param("example") ShareFileDOExample example);

    int updateByPrimaryKeySelective(ShareFileDO record);

    int updateByPrimaryKey(ShareFileDO record);
}