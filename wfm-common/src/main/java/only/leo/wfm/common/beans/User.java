package only.leo.wfm.common.beans;

public class User {
    private String userName;

    private String password;

    private Boolean enable;

    private Short roleId;

    public User(String userName, String password, Boolean enable, Short roleId) {
        this.userName = userName;
        this.password = password;
        this.enable = enable;
        this.roleId = roleId;
    }

    public UserDO toDO(){
        UserDO userDO = new UserDO();
        userDO.setEnable(enable);
        userDO.setPassword(password);
        userDO.setRoleId(roleId);
        userDO.setUserName(userName);
        return userDO;
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