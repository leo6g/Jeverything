package only.leo.wfm.core.service;

import only.leo.wfm.common.MemoryCache;
import only.leo.wfm.common.beans.*;
import only.leo.wfm.common.util.StringUtil;
import only.leo.wfm.core.dao.DirectoryDOMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DirectoryService implements IDirectoryService {
    @Autowired
    private DirectoryDOMapper directoryDOMapper;
    @Autowired
    private FileManagerService fileManagerService;

    @Override
    public int insertRecord(Directory record) {

        //TODO
        if(MemoryCache.cachePath2Id.containsKey(record.getPath())){
            return 1;
        };
        DirectoryDOExample example = new DirectoryDOExample();
        example.createCriteria().andPathEqualTo(record.getPath());
        List<DirectoryDO> temList = directoryDOMapper.selectByExample(example);
        if(temList!=null&&temList.size()>0){
            DirectoryDO tmp = temList.get(0);
            putCache(tmp.getId(),tmp.getPath());
            return 1;
        }
        DirectoryDO directoryDO = record.toDO();
        int c = directoryDOMapper.insert(directoryDO);
        record.setId(directoryDO.getId());
        putCache(record.getId(),record.getPath());
        return c;
    }
    @Override
    public void remove(Directory example){
        Integer id = example.getId();
        if(id==null) id = queryIdByPath(example.getPath());
        directoryDOMapper.deleteByPrimaryKey(id);
        removeCache(id,example.getPath());
        fileManagerService.removeByDir(id);
    }
    @Override
    public void removeByIds(List<Integer> ids){
        DirectoryDOExample example = new DirectoryDOExample();
        example.createCriteria().andIdIn(ids);
        directoryDOMapper.deleteByExample(example);
        removeCache(ids);
        fileManagerService.removeByDirs(ids);
    }
    public void initCache(){
        List<DirectoryVO> list = getList(new Directory());
        for(DirectoryVO vo:list){
            putCache(vo.getId(),vo.getPath());
        }
    }
    @Override
    public void rmRecursively(Directory example){
        Integer id = example.getId();
        String parentPath = example.getPath();
        if(id==null) id = queryIdByPath(parentPath);
        List<Integer> toDeleteIds = new ArrayList<>();
        toDeleteIds.add(id);
        for(Map.Entry<String,Integer> entry :MemoryCache.cachePath2Id.entrySet()){
            if(entry.getKey().startsWith(parentPath)){
                toDeleteIds.add(entry.getValue());
            }
        }
        removeByIds(toDeleteIds);
    }
    private void putCache(Integer id,String path){
        MemoryCache.cacheId2Path.put(id,path);
        MemoryCache.cachePath2Id.put(path,id);
    }
    private void removeCache(Integer id,String path){
        MemoryCache.cacheId2Path.remove(id);
        MemoryCache.cachePath2Id.remove(path);
    }
    private void removeCache(List<Integer> ids){
        for(Integer id:ids){
            String path = MemoryCache.cacheId2Path.get(id);
            if(path != null){
                MemoryCache.cachePath2Id.remove(path);
            }
            MemoryCache.cacheId2Path.remove(id);
        }
    }
    @Override
    public DirectoryVO queryById(int id) {
        DirectoryDO directoryDO = directoryDOMapper.selectByPrimaryKey(id);
        return directoryDO.toVO();
    }

    @Override
    public String queryPathById(int id) {
        String path = MemoryCache.cacheId2Path.get(id);
        if(path == null){
            DirectoryDO directoryDO = directoryDOMapper.selectByPrimaryKey(id);
            if(directoryDO!=null){
                path = directoryDO.getPath();
                MemoryCache.cacheId2Path.put(id,path);
            }
        }
        return path;
    }

    @Override
    public Integer queryIdByPath(String path) {
        Integer id = MemoryCache.cachePath2Id.get(path);
        if(id == null){
            DirectoryDOExample  example = new DirectoryDOExample();
            example.createCriteria().andPathEqualTo(path);
            List<DirectoryDO> list = directoryDOMapper.selectByExample(example);
            if(list!=null&&list.size()>0){
                id = list.get(0).getId();
                MemoryCache.cachePath2Id.put(path,id);
            }
        }

        return id;
    }

    @Override
    public List<DirectoryVO> getListByPath(String key) {
        List<DirectoryVO> list = new ArrayList<>();
        DirectoryDOExample example = new DirectoryDOExample();
        resolveKey(example.createCriteria(),key);
        List<DirectoryDO> tempList = directoryDOMapper.selectByExample(example);
        for(DirectoryDO directoryDO:tempList){
            list.add(directoryDO.toVO());
        }
        return list;
    }
    private void resolveKey(DirectoryDOExample.Criteria criteria,String key){
        if(StringUtil.isEmpty(key)) return;
        if(key.contains("*")){
            criteria.andPathLike(key.replaceAll("\\*","%").replaceAll("\\\\","\\\\\\\\"));
            return;
        }
        criteria.andPathEqualTo(key);
//        criteria.andPathLike(("%"+key+"%").replaceAll("\\\\","\\\\\\\\"));
    }
    @Override
    public List<DirectoryVO> getList(Directory directory) {
        List<DirectoryVO> list = new ArrayList<>();
        DirectoryDOExample example = new DirectoryDOExample();
        DirectoryDOExample.Criteria criteria = example.createCriteria();
        if(directory.getIndexId()!=null){
            criteria.andIndexIdEqualTo(directory.getIndexId());
        }
        List<DirectoryDO> tempList = directoryDOMapper.selectByExample(example);
        for(DirectoryDO directoryDO:tempList){
            list.add(directoryDO.toVO());
        }
        return list;
    }

}
