package only.leo.wfm.web.boot;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: LIBAO
 */
public class ResponseHandler {
    public static void sendError(String msg, HttpServletResponse response){
        try {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print(msg);
            response.getWriter().close();
        } catch (IOException e) {
        }
    }
}
