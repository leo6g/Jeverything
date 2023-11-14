package only.leo.wfm.core.service;

import only.leo.wfm.common.beans.BaseConfig;
import only.leo.wfm.common.beans.BaseConfigVO;
import only.leo.wfm.common.beans.User;
import only.leo.wfm.common.beans.UserVO;

/**
 * @Author: LEO
 * @Date: 2021/9/15 14:07
 */
public interface IBaseConfigService {
    public BaseConfigVO getBaseConfig(int id);
    public void update(BaseConfig baseConfig);
    public void insert(BaseConfig baseConfig);
}
