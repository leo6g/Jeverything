package only.leo.wfm.web.boot.callback;


import only.leo.wfm.common.Constants;
import only.leo.wfm.common.FileType;
import only.leo.wfm.common.MemoryCache;
import only.leo.wfm.common.PlatformSystem;
import only.leo.wfm.common.ResultBean;
import only.leo.wfm.common.beans.BaseConfig;
import only.leo.wfm.common.beans.BaseConfigVO;
import only.leo.wfm.common.beans.Directory;
import only.leo.wfm.common.beans.DirectoryVO;
import only.leo.wfm.common.beans.IndexDirectoryVO;
import only.leo.wfm.common.beans.User;
import only.leo.wfm.common.beans.UserVO;
import only.leo.wfm.common.util.NetUtil;
import only.leo.wfm.common.util.StringUtil;
import only.leo.wfm.common.util.SystemUtil;
import only.leo.wfm.core.dao.H2DAO;
import only.leo.wfm.core.runnable.idea.ProcessHandler;
import only.leo.wfm.core.service.FileDetectedService;
import only.leo.wfm.core.service.IBaseConfigService;
import only.leo.wfm.core.service.IDirectoryService;
import only.leo.wfm.core.service.IUserService;
import only.leo.wfm.core.service.IndexService;
import only.leo.wfm.web.boot.controller.IndexController;
import org.apache.log4j.Logger;
import org.h2.tools.Server;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author: leo
 * @Email: leosfox@foxmail.com
 * @Version 1.0
 */
@Component("appStarter")
public class AppStarter implements BootCallBack , InitializingBean {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private Environment environment;
    @Autowired
    private Server h2Server;
    @Autowired
    private ProcessHandler processHandler;
    @Autowired
    private H2DAO h2DAO;
    @Autowired
    private IUserService userService;
    @Autowired
    private IBaseConfigService baseConfigService;
    @Autowired
    private FileDetectedService fileDetectedService;
    @Autowired
    private IndexService indexDirectoryService;
    @Autowired
    private IndexController indexController;
    @Autowired
    private IDirectoryService directoryService;

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream in = AppStarter.class.getClassLoader().getResourceAsStream("ddl.sql");
            InputStreamReader r = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(r);
            String line = "";
            StringBuilder ddl = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if(StringUtil.isEmpty(line)) continue;
                ddl.append(line);
            }
            h2DAO.executeSQL(ddl.toString());
            if(in!=null) in.close();
            if(r!=null) r.close();
            if(br!=null) br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStarted(ApplicationContext context) {
        if(SystemUtil.getPlatformSystem() == PlatformSystem.WINDOWS)systemTray();
        initData();
        openBrowser();
        initCache();
        startListenChange();
        logger.info("start successfully");
    }
    private void startListenChange(){
        //add index path to listener
        fileDetectedService.startIndexListen();
    }
    private void initData(){
        UserVO userVO = userService.getUserByName("admin");
        if(userVO==null){
            userService.add(new User("admin","123456",true,(short)1));
        }
        BaseConfigVO baseConfigVO = baseConfigService.getBaseConfig(1);
        if(baseConfigVO == null){
            BaseConfig config = new BaseConfig();
            config.setDomain(getDomain());
            config.setOtherFiles("");
            config.setInit(false);
            config.setId(1);
            baseConfigService.insert(config);
        } else {
            //fileTypeMap 添加other
            String otherFiles = baseConfigVO.getOtherFiles();
            String[] arr = otherFiles.split(",");
            for(String s:arr){
                if(StringUtil.isNotEmpty(s)){
                    Constants.fileTypeMap.put(s, FileType.OTHER);
                }
            }
        }

    }

    private void systemTray(){
        if(SystemTray.isSupported()){
            TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/image/clientICO.png")));
            trayIcon.setToolTip("JEverything");
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getButton()==1&&e.getClickCount()==2){
                        openBrowser();
                    }
                }
                @Override
                public void mousePressed(MouseEvent e) {

                }
                @Override
                public void mouseReleased(MouseEvent e) {

                }
                @Override
                public void mouseEntered(MouseEvent e) {

                }
                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            PopupMenu popupMenu = new PopupMenu();
            MenuItem exit = new MenuItem("Exit");
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SystemTray.getSystemTray().remove(trayIcon);
                    processHandler.onClose();
                    Runtime.getRuntime().halt(0);
                }
            });
            MenuItem open = new MenuItem("Open");
            open.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openBrowser();
                }
            });
            popupMenu.add(open);
            popupMenu.add(exit);
            trayIcon.setPopupMenu(popupMenu);
            try {
                SystemTray.getSystemTray().add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 初始化索引目录缓存，索引目录检测
     */
    private void initCache(){
        indexDirectoryService.initIndexCache();
        directoryService.initCache();
    }
    /**
     * 全盘扫描监听
     */
    public void scanAll(){
        ResultBean resultBean =  indexController.getList();
        if(resultBean.getBeans()!=null&&resultBean.getBeans().size()>0) return;
        File[] roots = File.listRoots();
        logger.info("async start init index directory...");
        for(File root:roots){
            new Thread(){
                @Override
                public void run() {
                    indexController.createIndex(root.getAbsolutePath(),"local",null,null,true);
                }
            }.start();
        }
    }
    private String getDomain(){
        int port = Integer.parseInt(environment.getProperty("server.port","80"));
        String hostIP = NetUtil.getIntranetIp();
        return hostIP+":"+port;
    }
    private void openBrowser(){
        BaseConfigVO baseConfigVO = baseConfigService.getBaseConfig(1);
        String domain =  baseConfigVO.getDomain();
        String uri = "http://"+ (StringUtil.isEmpty(domain)?getDomain():domain) +"/?#/";
        SystemUtil.browseURI(uri);
    }
    private AppStarter() {}
}
