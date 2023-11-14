package only.leo.wfm.common.util;


import java.util.concurrent.TimeUnit;

/**
 * @Author: leo
 * @Email: leosfox@foxmail.com
 * @Version 1.0
 */
public class CMDExcutor {
    private Runtime runtime = Runtime.getRuntime();
    private CMDExcutor(){};
    private static CMDExcutor _instance=new CMDExcutor();
    public static CMDExcutor _instance(){
        return _instance;
    }
    public String exec(String cmd){
        StringBuilder sb = new StringBuilder();
        String cmd_prefix = "cmd.exe /c ";
        try {
            Process pro = runtime.exec(cmd_prefix+cmd);
            if(!pro.waitFor(30, TimeUnit.SECONDS)){
               pro.destroy();
            }
        } catch (Exception e) {
            sb.append("执行cmd出错 exception:"+e.getMessage());
        }
        return sb.toString();
    }
}
