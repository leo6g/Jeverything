package only.leo.wfm.core.service;

import only.leo.wfm.common.MemoryCache;
import only.leo.wfm.common.ProtocolType;
import only.leo.wfm.common.beans.*;
import only.leo.wfm.core.Pipeline;
import only.leo.wfm.core.dao.IndexDirectoryDOMapper;
import only.leo.wfm.core.scanner.Scanner;
import only.leo.wfm.core.scanner.ScannerFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IndexService implements IIndexService {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private IndexDirectoryDOMapper indexDirectoryDOMapper;
    @Autowired
    private FileManagerService fileManagerService;
    @Autowired
    private FileDetectedService fileDetectedService;
    @Autowired
    private Pipeline pipeline;

    @Autowired
    private ScannerFactory scannerFactory;
    public void addIndex(String path,String type,String authentication,boolean listen) throws Exception{
        IndexDirectoryVO exist = queryByLocation(path);
        IndexDirectory indexDirectory = new IndexDirectory();
        indexDirectory.setAuthentication(authentication);
        indexDirectory.setIndexTime(new Date());
        boolean remote =!"LOCAL".equalsIgnoreCase(type);
        ProtocolType  protocolType = ProtocolType.UNSUPPORTED;
        if(remote){
            if(path.startsWith("smb:")){
                protocolType = ProtocolType.SMB;
            }else if(path.startsWith("ftp:")){
                protocolType = ProtocolType.FTP;
            }
        }else {
            protocolType = ProtocolType.LOCAL;
        }
        indexDirectory.setProtocol(protocolType.getCode());
        indexDirectory.setIsRemote(remote);
        if(exist==null){
            indexDirectory.setPath(path);
            indexDirectory.setListen(listen);
            addRecord(indexDirectory);
        }else {
            indexDirectory.setId(exist.getId());
            update(indexDirectory);
        }
        index(path,indexDirectory);
    }

    @Override
    public void index(String path, IndexDirectory indexDirectory) throws Exception {
        Scanner scanner = scannerFactory.getScanner(ProtocolType.fromCode(indexDirectory.getProtocol())
                ,path,indexDirectory);
        scanner.start();
        logger.info(path+"--index successfully");
    }

    @Override
    public void reIndex(Short id) throws Exception{
        IndexDirectory indexDirectory = new IndexDirectory();
        IndexDirectoryVO indexDirectoryVO = queryById(id);
        indexDirectory.setIndexTime(new Date());
        indexDirectory.setPath(indexDirectoryVO.getPath());
        indexDirectory.setId(indexDirectoryVO.getId());
        indexDirectory.setIsRemote(indexDirectoryVO.getIsRemote());
        indexDirectory.setProtocol(ProtocolType.fromName(indexDirectoryVO.getProtocol()).getCode());
        indexDirectory.setAuthentication(queryAuth(id));
        update(indexDirectory);
        ProtocolType protocolType = ProtocolType.fromName(indexDirectoryVO.getProtocol());
        Scanner scanner = scannerFactory.getScanner(protocolType,indexDirectory.getPath(),indexDirectory);
        scanner.start();
        logger.info(indexDirectory.getPath()+"--index successfully");
    }

    @Override
    public int addRecord(IndexDirectory record) {
        IndexDirectoryDO indexDirectoryDO = record.toDO();
        IndexDirectoryDOExample example = new IndexDirectoryDOExample();
        example.createCriteria().andPathEqualTo(record.getPath());
        List<IndexDirectoryDO> temList = indexDirectoryDOMapper.selectByExample(example);
        if(temList!=null&&temList.size()>0){
            record.setId(temList.get(0).getId());
            return 0;
        }
        int c = indexDirectoryDOMapper.insert(indexDirectoryDO);
        record.setId(indexDirectoryDO.getId());

        addIndexDirectory(record.getId(),indexDirectoryDO.toVO());
        return c;
    }

    @Override
    public IndexDirectoryVO queryByLocation(String location) {
        IndexDirectoryVO matchVO = null;
        for(IndexDirectoryVO vo:MemoryCache.indexDirectoryVOCache){
            if(location.startsWith(vo.getPath())) matchVO = vo;
        }
        return matchVO;
    }

    @Override
    public void removeIndex(Short id) {
        indexDirectoryDOMapper.deleteByPrimaryKey(id);
        fileManagerService.removeByIndex(id);
        removeIndexDirectory(id);
    }

    @Override
    public String queryAuth(Short id) {
        IndexDirectoryDO indexDirectoryDO = indexDirectoryDOMapper.selectByPrimaryKey(id);
        return indexDirectoryDO.getAuthentication();
    }

    @Override
    public IndexDirectoryVO queryById(Short id) {
        IndexDirectoryVO indexDirectoryVO = MemoryCache.cacheIndexDirectory.get(id);
        if(indexDirectoryVO==null){
            IndexDirectoryDO indexDirectoryDO = indexDirectoryDOMapper.selectByPrimaryKey(id);
            indexDirectoryVO = indexDirectoryDO.toVO();
            addIndexDirectory(id,indexDirectoryVO);
        }
        return indexDirectoryVO;
    }
    public int update(IndexDirectory example){
        int i = indexDirectoryDOMapper.updateByPrimaryKeySelective(example.toDO());
        IndexDirectoryDO indexDirectoryDO = indexDirectoryDOMapper.selectByPrimaryKey(example.getId());
        IndexDirectoryVO indexDirectoryVO = indexDirectoryDO.toVO();
        updateIndexDirectory(example.getId(),indexDirectoryVO);
        return i;
    }
    public void initIndexCache(){
        List<IndexDirectoryVO> all = getList();
        for(IndexDirectoryVO vo:all){
            MemoryCache.cacheIndexDirectory.put(vo.getId(),vo);
        }
        MemoryCache.indexDirectoryVOCache.addAll(all);
    }
    private void updateIndexDirectory(short id,IndexDirectoryVO indexDirectoryVO){
        MemoryCache.cacheIndexDirectory.put(id,indexDirectoryVO);
        MemoryCache.indexDirectoryVOCache.clear();
        List<IndexDirectoryVO> all = getList();
        MemoryCache.indexDirectoryVOCache.addAll(all);
        fileDetectedService.startIndexListen();
    }
    private void addIndexDirectory(short id,IndexDirectoryVO indexDirectoryVO){
        MemoryCache.cacheIndexDirectory.put(id,indexDirectoryVO);
        if(!MemoryCache.indexDirectoryVOCache.contains(indexDirectoryVO)){
            MemoryCache.indexDirectoryVOCache.add(indexDirectoryVO);
        }
        if(indexDirectoryVO.getListen()&&!indexDirectoryVO.getIsRemote()){
            fileDetectedService.restartIndexListen();
        }
    }
    private void removeIndexDirectory(short id){
        IndexDirectoryVO indexDirectoryVO = MemoryCache.cacheIndexDirectory.remove(id);
        MemoryCache.indexDirectoryVOCache.remove(indexDirectoryVO);
        if(indexDirectoryVO.getListen()&&!indexDirectoryVO.getIsRemote()){
            fileDetectedService.restartIndexListen();
        }
    }
    @Override
    public List<IndexDirectoryVO> getList() {
        List<IndexDirectoryVO> list = new ArrayList<>();
        IndexDirectoryDOExample example = new IndexDirectoryDOExample();
        List<IndexDirectoryDO> temList= indexDirectoryDOMapper.selectByExample(example);
        for(IndexDirectoryDO bean:temList){
            long count = fileManagerService.countByIndexId(bean.getId());
            IndexDirectoryVO vo = bean.toVO();
            vo.setFileCount(count);
            list.add(vo);
        }
        return list;
    }
}
