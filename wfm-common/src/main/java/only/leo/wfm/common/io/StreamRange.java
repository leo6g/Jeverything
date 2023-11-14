package only.leo.wfm.common.io;

import only.leo.wfm.common.util.StringUtil;

/**
 * @Author: LEO
 * @Date: 2021/10/19 17:23
 */
public class StreamRange {
    private long startPos;
    private long endPos;
    private long length;
    private String original;
    private boolean toEnd;
    private boolean fromStart;

    /**
     * 支持多范围解析
     * @param rangeStr
     * @return
     */
    private static StreamRange[] resolveRange(String rangeStr){
        rangeStr = rangeStr.replace("bytes=","");
        String[] arr = rangeStr.split(",");
        for (String s:arr){

        }
        return new StreamRange[1];
    }
    public static StreamRange defaultRange(){
        StreamRange streamRange = new StreamRange();
        streamRange.setFromStart(true);
        streamRange.setStartPos(0l);
        return streamRange;
    }
    public static StreamRange resolveRange0(String rangeStr0){
        if(StringUtil.isEmpty(rangeStr0))return defaultRange();
        StreamRange streamRange = new StreamRange();
        streamRange.setOriginal(rangeStr0);
        rangeStr0 = rangeStr0.replace("bytes=","");
        String[] arr = rangeStr0.split("-");
        if(arr.length==1){
            streamRange.setFromStart(true);
            streamRange.setStartPos(Long.valueOf(arr[0]));
        }else if(arr.length==2) {
            if(StringUtil.isNotEmpty(arr[0])){
                long start = Long.valueOf(arr[0]);
                long end = Long.valueOf(arr[1]);
                streamRange.setStartPos(start);
                streamRange.setEndPos(end);
                streamRange.setLength(end-start+1);
            }else {
                streamRange.setToEnd(true);
                streamRange.setLength(Long.valueOf(arr[1]));
            }
        }else {
            defaultRange();
        }
        return streamRange;
    }

    public long getStartPos() {
        return startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

    public long getEndPos() {
        return endPos;
    }

    public void setEndPos(long endPos) {
        this.endPos = endPos;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public boolean isToEnd() {
        return toEnd;
    }

    public void setToEnd(boolean toEnd) {
        this.toEnd = toEnd;
    }

    public boolean isFromStart() {
        return fromStart;
    }

    @Override
    public String toString() {
        return "StreamRange{" +
                "startPos=" + startPos +
                ", endPos=" + endPos +
                ", length=" + length +
                ", original='" + original + '\'' +
                ", toEnd=" + toEnd +
                ", fromStart=" + fromStart +
                '}';
    }

    public void setFromStart(boolean fromStart) {
        this.fromStart = fromStart;
    }
}
