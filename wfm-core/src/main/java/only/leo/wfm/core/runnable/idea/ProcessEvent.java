package only.leo.wfm.core.runnable.idea;

import java.util.Objects;

/**
 * @Author: LIBAO
 */
public class ProcessEvent {
    public enum WatcherOp { GIVEUP, RESET, UNWATCHEABLE, REMAP, MESSAGE, CREATE, DELETE, STATS, CHANGE, DIRTY, RECDIRTY }
    private WatcherOp op;
    private String value;

    public ProcessEvent(WatcherOp op, String value) {
        this.op = op;
        this.value = value;
    }

    public WatcherOp getOp() {
        return op;
    }

    public String getValue() {
        return value;
    }


    public boolean equals(ProcessEvent that) {
        if (this == that) return true;
        if (that == null) return false;
        return op == that.op &&
                Objects.equals(value, that.value);
    }

    @Override
    public String toString() {
        return "ProcessEvent{" +
                "op=" + op +
                ", value='" + value + '\'' +
                '}';
    }
}
