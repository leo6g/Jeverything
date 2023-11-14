package only.leo.wfm.core;

/**
 * @Author: LEO
 * @Date: 2021/8/17 10:57
 */
public interface Pipeline<T> {
    void stop();
    void start();
    void close();
    PipeLineState getState();
    boolean isEmpty();
    void submit(T t);
    T take() throws InterruptedException;
}
