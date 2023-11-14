package only.leo.wfm;

import only.leo.wfm.web.boot.SpringInitor;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: LEO
 * @Date: 2021/8/17 14:31
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringInitor.init(Application.class,args);
    }
}
