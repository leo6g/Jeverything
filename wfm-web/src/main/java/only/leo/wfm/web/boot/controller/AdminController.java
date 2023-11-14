package only.leo.wfm.web.boot.controller;

import only.leo.wfm.common.ResultBean;
import only.leo.wfm.common.beans.BaseConfig;
import only.leo.wfm.common.beans.BaseConfigVO;
import only.leo.wfm.common.beans.IntranetIpVO;
import only.leo.wfm.common.beans.User;
import only.leo.wfm.common.beans.UserVO;
import only.leo.wfm.common.util.NetUtil;
import only.leo.wfm.common.util.StringUtil;
import only.leo.wfm.core.service.IBaseConfigService;
import only.leo.wfm.core.service.IUserService;
import only.leo.wfm.web.boot.callback.AppStarter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private IUserService userService;
    @Autowired
    private IBaseConfigService baseConfigService;
    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    @Autowired
    private Environment environment;
    @Autowired
    private AppStarter appStarter;

    @RequestMapping(path="/updatePassword",method = RequestMethod.POST)
    public ResultBean updatePassword(String oldPass,String newPass){
        String msg = ResultBean.MSG.OK;
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser==null) return new ResultBean("用户未登录",ResultBean.CODE.ERROR);
        String userName = currentUser.getName();
        UserVO userVO = userService.getUserByName(userName);
        if(userVO==null)return new ResultBean("用户不存在",ResultBean.CODE.ERROR);
        if(!oldPass.equals(userVO.getPassword()))return new ResultBean("旧密码错误",ResultBean.CODE.ERROR);
        try {
            userDetailsManager.changePassword(oldPass,newPass);
        } catch (Exception e) {
            return new ResultBean(e.getMessage(),ResultBean.CODE.ERROR);
        }
        userService.update(new User(currentUser.getName(),newPass,true,(short)1));
        return new ResultBean(msg,ResultBean.CODE.SUCCESS);
    }
    @RequestMapping(path="/getBaseConfig",method = RequestMethod.GET)
    public ResultBean getBaseConfig(){
        String msg = ResultBean.MSG.OK;
        BaseConfigVO baseConfigVO = baseConfigService.getBaseConfig(1);
        ResultBean resultBean = new ResultBean(msg,ResultBean.CODE.SUCCESS);
        resultBean.setBean(baseConfigVO);
        return resultBean;
    }
    @RequestMapping(path="/getIntranetIps",method = RequestMethod.GET)
    public ResultBean getIntranetIps(){
        String msg = ResultBean.MSG.OK;
        ResultBean resultBean = new ResultBean(msg,ResultBean.CODE.SUCCESS);
        int port = Integer.parseInt(environment.getProperty("server.port","80"));
        String[] ips = NetUtil.getIntranetIps();
        List<IntranetIpVO> intranetIpVOS = new ArrayList<>();
        for(String ip:ips){
            intranetIpVOS.add(new IntranetIpVO(ip+":"+port));
        }
        resultBean.setBeans(intranetIpVOS);
        return resultBean;
    }
    @RequestMapping(path="/initConfig",method = RequestMethod.POST)
    public ResultBean initConfig(String domain,String newPass,Boolean scanALL){
        String msg = ResultBean.MSG.OK;
        BaseConfigVO baseConfigVO = baseConfigService.getBaseConfig(1);
        if(baseConfigVO.getInit()){
            msg = ResultBean.MSG.ERROR;
            ResultBean resultBean = new ResultBean(msg,ResultBean.CODE.ERROR);
            return resultBean;
        }
        BaseConfig config = new BaseConfig();
        config.setId(1);
        config.setDomain(domain);
        config.setInit(true);
        baseConfigService.update(config);
        if(StringUtil.isNotEmpty(newPass)){
            userService.update(new User("admin",newPass,true,(short)1));
            userDetailsManager.updateUser(org.springframework.security.core.userdetails.User.withUsername("admin").password(newPass).roles("admin").build());        }
        if(scanALL){
            appStarter.scanAll();
        }
        ResultBean resultBean = new ResultBean(msg,ResultBean.CODE.SUCCESS);
        return resultBean;
    }
    @RequestMapping(path="/setBaseConfig",method = RequestMethod.POST)
    public ResultBean updatePassword(BaseConfig config){
        String msg = ResultBean.MSG.OK;
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        if(currentUser==null) return new ResultBean("用户未登录",ResultBean.CODE.ERROR);
        config.setInit(true);
        baseConfigService.update(config);
        return new ResultBean(msg,ResultBean.CODE.SUCCESS);
    }
}
