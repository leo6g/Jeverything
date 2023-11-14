package only.leo.wfm.core.service;

import only.leo.wfm.common.beans.ShareFile;

/**
 * @Author: LEO
 * @Date: 2021/9/15 14:07
 */
public interface IShareFileService {
    public int add(ShareFile shareFile);
    public int update(ShareFile shareFile);
    public int remove(ShareFile shareFile);
}
