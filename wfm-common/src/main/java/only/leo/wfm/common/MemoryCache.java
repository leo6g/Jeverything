package only.leo.wfm.common;

import only.leo.wfm.common.beans.IndexDirectoryVO;
import only.leo.wfm.common.beans.ShareFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: LEO
 * @Date: 2021/9/16 11:06
 */
public interface MemoryCache {
    Map<Integer,String> cacheId2Path = new HashMap<Integer, String>();
    Map<String,Integer> cachePath2Id = new HashMap<String, Integer>();
    Map<String, ShareFile> cacheSharedFile= new HashMap<String, ShareFile>();
    Map<String, String> cacheHttpStream= new HashMap<String, String>();
    Map<Short, IndexDirectoryVO> cacheIndexDirectory = new HashMap<Short, IndexDirectoryVO>();
    List<IndexDirectoryVO> indexDirectoryVOCache = new ArrayList<>();
}
