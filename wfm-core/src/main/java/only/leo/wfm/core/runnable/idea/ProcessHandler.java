package only.leo.wfm.core.runnable.idea;

import java.io.IOException;
import java.util.List;

/**
 * @Author: LIBAO
 */
public interface ProcessHandler {
    void listenPath(List<String> paths) throws IOException;
    void notifyTerminated(int exitCode);
    void startProcess(boolean restart) throws IOException;
    void notifyTextAvailable(String text);
    void onClose();
}
