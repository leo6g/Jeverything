package only.leo.wfm.core;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author: LEO
 * @Date: 2021/8/16 16:26
 */
public abstract class AbstractPipeline<T> implements Pipeline<T> {
    private LinkedBlockingQueue<T> sourceQueue = new LinkedBlockingQueue<T>(1024*10);
    private Runnable sink;
    private Thread thread;
    private PipeLineState state = PipeLineState.NEW;
    public void submit(T t){
        try {
            sourceQueue.put(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public T take() throws InterruptedException {
        return sourceQueue.take();
    }

    public synchronized void start(){
        if(this.state== PipeLineState.START||this.state==PipeLineState.TERMINATE) return;
        this.state = PipeLineState.START;
        this.thread = new Thread(sink);
        this.thread.setName("sink-thread-"+this.hashCode());
        this.thread.start();
    }
    public void stop(){
        this.state = PipeLineState.STOP;
    }
    public void close(){
        this.state = PipeLineState.TERMINATE;
        this.thread.interrupt();
    }
    public PipeLineState getState() {
        return state;
    }

    @Override
    public boolean isEmpty() {
        return this.sourceQueue.isEmpty();
    }

    public void setState(PipeLineState state) {
        this.state = state;
    }

    public Runnable getSink() {
        return sink;
    }

    public void setSink(Runnable sink) {
        this.sink = sink;
    }
}
