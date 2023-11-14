package only.leo.wfm.core.service;

import only.leo.wfm.common.beans.ShareFile;
import only.leo.wfm.core.dao.ShareFileDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: LEO
 * @Date: 2021/9/16 8:33
 */
@Service
public class ShareFileService implements IShareFileService{
    @Autowired
    private ShareFileDOMapper shareFileDOMapper;
    @Override
    public int add(ShareFile shareFile) {
        return shareFileDOMapper.insert(shareFile.toDO());
    }

    @Override
    public int update(ShareFile shareFile) {
        return shareFileDOMapper.updateByPrimaryKey(shareFile.toDO());
    }

    @Override
    public int remove(ShareFile shareFile) {
        return shareFileDOMapper.deleteByPrimaryKey(shareFile.getCode());
    }
}
