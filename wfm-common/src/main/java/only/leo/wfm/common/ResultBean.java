package only.leo.wfm.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: leo
 * @Email: leosfox@foxmail.com
 * @Version 1.0
 */
public class ResultBean {
    public interface MSG{
        String OK = "OK";
        String ERROR = "ERROR";
    }
    public interface CODE{
        int SUCCESS = 0;
        int ERROR = -1;
    }
    private String msg;
    private Integer code;
    private Object bean;
    private List beans = new ArrayList();

    public ResultBean() {
    }
    public ResultBean(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    public ResultBean(String msg, Integer code, Object bean) {
        this.msg = msg;
        this.code = code;
        this.bean = bean;
    }

    public ResultBean(String msg, Integer code, List<Object> beans) {
        this.msg = msg;
        this.code = code;
        this.beans = beans;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", bean=" + bean +
                ", beans=" + beans +
                '}';
    }
    public List<Object> getBeans() {
        return beans;
    }

    public void setBeans(List beans) {
        this.beans = beans;
    }
}
