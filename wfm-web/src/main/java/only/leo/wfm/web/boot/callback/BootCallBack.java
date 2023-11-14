package only.leo.wfm.web.boot.callback;

import org.springframework.context.ApplicationContext;

/**
 * @Author: leo
 * @Email: leosfox@foxmail.com
 * @Version 1.0
 */
public interface BootCallBack {
    void onStarted(ApplicationContext context);
}
