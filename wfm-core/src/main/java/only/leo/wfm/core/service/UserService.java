package only.leo.wfm.core.service;

import only.leo.wfm.common.beans.User;
import only.leo.wfm.common.beans.UserDO;
import only.leo.wfm.common.beans.UserVO;
import only.leo.wfm.core.dao.UserDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: LEO
 * @Date: 2021/9/15 14:09
 */
@Service
public class UserService implements IUserService{
    @Autowired
    private UserDOMapper userDOMapper;
    @Override
    public int add(User user) {
        return userDOMapper.insert(user.toDO());
    }

    @Override
    public int update(User user) {
        return userDOMapper.updateByPrimaryKey(user.toDO());
    }

    @Override
    public int remove(User user) {
        return userDOMapper.deleteByPrimaryKey(user.getUserName());
    }

    @Override
    public UserVO getUserByName(String userName) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(userName);
        if(userDO==null)return null;
        return userDO.toVO();
    }
}
