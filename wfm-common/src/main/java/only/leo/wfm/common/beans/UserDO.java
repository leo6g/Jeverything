package only.leo.wfm.common.beans;

public class UserDO {
    private String userName;

    private String password;

    private Boolean enable;

    private Short roleId;
    public UserVO toVO(){
        UserVO userVO = new UserVO();
        userVO.setEnable(enable);
        userVO.setPassword(password);
        userVO.setUserName(userName);
        return userVO;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Short getRoleId() {
        return roleId;
    }

    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }
}