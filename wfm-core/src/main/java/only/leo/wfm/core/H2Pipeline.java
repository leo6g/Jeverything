package only.leo.wfm.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: LEO
 * @Date: 2021/8/16 16:48
 */
@Component
public class H2Pipeline extends AbstractPipeline {
    @Autowired
    public void sink(Runnable h2Sink){
        this.setSink(h2Sink);
    }
}
