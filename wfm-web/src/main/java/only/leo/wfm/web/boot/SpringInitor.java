package only.leo.wfm.web.boot;

import only.leo.wfm.web.boot.callback.AppStarter;
import only.leo.wfm.web.boot.callback.BootCallBack;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: leo
 * @Email: leosfox@foxmail.com
 * @Version 1.0
 */
public class SpringInitor {
    public static void init(final Class<?> mainClass,String[] args){
        ApplicationContext c = new SpringApplicationBuilder().initializers(getInitializers()).sources(mainClass).headless(false).run(args);
        initInternal(mainClass,c,args);
    }
    private static void initInternal(final Class<?> mainClass, ApplicationContext c,String[] args){
        initProperties();
        BootCallBack callBack = c.getBean("appStarter", AppStarter.class);
        initInternal0(mainClass,c,callBack,args);
    }
    private static void initProperties(){

    }
    private static void initInternal0(final Class<?> mainClass, ApplicationContext c, BootCallBack callBack, String[] args){
        callBack.onStarted(c);
    }
    private static ApplicationContextInitializer[] getInitializers(){
        List<ApplicationContextInitializer> a = new ArrayList<ApplicationContextInitializer>();
        return a.toArray(new ApplicationContextInitializer[a.size()]);
    }
}
