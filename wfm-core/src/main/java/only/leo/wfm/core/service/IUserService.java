package only.leo.wfm.core.service;

import only.leo.wfm.common.beans.User;
import only.leo.wfm.common.beans.UserVO;

/**
 * @Author: LEO
 * @Date: 2021/9/15 14:07
 */
public interface IUserService {
    public int add(User user);
    public int update(User user);
    public int remove(User user);
    public UserVO getUserByName(String userName);
}
