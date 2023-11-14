package only.leo.wfm.core.service;

import only.leo.wfm.common.Constants;
import only.leo.wfm.common.FileType;
import only.leo.wfm.common.beans.BaseConfig;
import only.leo.wfm.common.beans.BaseConfigDO;
import only.leo.wfm.common.beans.BaseConfigVO;
import only.leo.wfm.common.beans.User;
import only.leo.wfm.common.beans.UserDO;
import only.leo.wfm.common.beans.UserVO;
import only.leo.wfm.common.util.StringUtil;
import only.leo.wfm.core.dao.BaseConfigDOMapper;
import only.leo.wfm.core.dao.UserDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: LEO
 * @Date: 2021/9/15 14:09
 */
@Service
public class BaseConfigService implements IBaseConfigService{
    @Autowired
    private BaseConfigDOMapper baseConfigDOMapper;
    @Override
    public BaseConfigVO getBaseConfig(int id) {
        BaseConfigDO baseConfigDO = baseConfigDOMapper.selectByPrimaryKey(1);
        if(baseConfigDO == null){
            return null;
        }
        return baseConfigDO.toVO();
    }

    @Override
    public void update(BaseConfig baseConfig) {
        BaseConfigDO baseConfigDO = baseConfig.toDO();
        baseConfigDO.setId(1);
        baseConfigDOMapper.updateByPrimaryKeySelective(baseConfigDO);
        String otherFiles = baseConfigDO.getOtherFiles();
        if(StringUtil.isNotEmpty(otherFiles)){
            String[] arr = otherFiles.split(",");
            for(String s:arr){
                if(StringUtil.isNotEmpty(s)){
                    Constants.fileTypeMap.put(s, FileType.OTHER);
                }
            }
        }
    }

    @Override
    public void insert(BaseConfig baseConfig) {
        baseConfigDOMapper.insert(baseConfig.toDO());
    }
}
