package only.leo.wfm.core.service;

import only.leo.wfm.common.beans.*;
import only.leo.wfm.common.util.FileUtil;
import only.leo.wfm.common.util.StringUtil;
import only.leo.wfm.core.dao.FileMetaDOMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@DependsOn("appStarter")
public class FileManagerService implements IFileManagerService {
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private FileMetaDOMapper fileMetaDOMapper;
    @Autowired
    private IDirectoryService directoryService;
    @Autowired
    private IIndexService indexService;
    private List<FileMetaDO> bufferList = new ArrayList<>();
    private long lastInsertTime;
    private long insertDuration = 15000;
    @Override
    public List<FileMetaVO> search(String key, int top,String fileType) {
        List<FileMetaVO> list = new ArrayList<>();
        try {
            FileMetaDOExample example = new FileMetaDOExample();
            resolveKey(example,key,fileType);
            example.setTop(top);
            List<FileMetaDO> temList= fileMetaDOMapper.selectByExample(example);
            for(FileMetaDO bean:temList){
                FileMetaVO fileMetaVO = bean.toVO();
                fileMetaVO.setPath(directoryService.queryPathById(bean.getDirectoryId()));
                fileMetaVO.setRemote(indexService.queryById(bean.getIndexId()).getIsRemote());
                list.add(fileMetaVO);
            }
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
        return list;
    }

   /* public static void main(String[] args) {
        //解析key path:xxx file:xxxx
        String s = "file:dsd path:234 file：ds";
        String[] arr = s.split("file[：:]");
        for(String a:arr){
            String[] arr2 = a.split("path[：:]");
            for(int i =0;i<arr2.length;i++){
                if(i==0){
                    System.out.println("file:"+arr2[i]);
                }else {
                    System.out.println("path:"+arr2[i]);
                }
            }
        }
    }*/

    //解析key (file:a b !c path:D:\ds*)|(file:b d !f path:D:\e*)
    private void resolveKey(FileMetaDOExample example,String key,String fileType){
        if(StringUtil.isEmpty(key)) return;
        key = key.trim();
        String[] arr = key.split("\\|");
        for(String s:arr){
            resolveKey0(example.or(),s,fileType);
        }
    }
    private void resolveKey0(FileMetaDOExample.Criteria criteria,String key,String fileType){
        String[] arr1 = key.split("file[：:]");
        for(String a:arr1){
            String[] arr2 = a.split("path[：:]");
            for(int i =0;i<arr2.length;i++){
                if(i==0){
                    resolveFileMatch(criteria,arr2[i],fileType);
                }else {
                    resolvePathMatch(criteria,arr2[i],fileType);
                }
            }
        }
        if(!"ALL".equals(fileType)) criteria.andFileTypeEqualTo(FileUtil.getFileCode(fileType));
    }
    private void resolveFileMatch(FileMetaDOExample.Criteria criteria,String fileMatch,String fileType){
        fileMatch = fileMatch.trim();
        if(StringUtil.isEmpty(fileMatch)) return;
        //resolve or
        resolveFileMatch0(criteria,fileMatch,fileType);
    }

    private void resolvePathMatch(FileMetaDOExample.Criteria criteria,String pathMatch,String fileType){
        pathMatch = pathMatch.trim();
        if(StringUtil.isEmpty(pathMatch)) return;
        //resolve or
        resolvePathMatch0(criteria,pathMatch,fileType);
    }

    private void resolveFileMatch0(FileMetaDOExample.Criteria criteria,String fileMatch0,String fileType){
        fileMatch0 = fileMatch0.trim();
        if(StringUtil.isEmpty(fileMatch0)) return;
        String[] arr = fileMatch0.replaceAll("[ ]+"," ").split(" ");
        for(String s:arr){
            boolean notLike = false;
            if(s.startsWith("!")||s.startsWith("！")){
                //handle not like
                s = s.substring(1);
                notLike = true;
            }
            String pattern = "";
            if(s.contains("*")){
                pattern = s.replaceAll("\\*","%");
            }else {
                pattern = "%"+s+"%";
            }
            if(notLike){
                criteria.andFileNameNotLike(pattern);
            }else {
                criteria.andFileNameLike(pattern);
            }
        }

    }

    private void resolvePathMatch0(FileMetaDOExample.Criteria criteria,String pathMatch0,String fileType){
        pathMatch0 = pathMatch0.trim();
        if(StringUtil.isEmpty(pathMatch0)) return;
        List<DirectoryVO> list = directoryService.getListByPath(pathMatch0);
            List<Integer> values = new ArrayList<>();
            for(DirectoryVO vo:list){
                values.add(vo.getId());
            }
            if(CollectionUtils.isEmpty(values)){
                values.add(-1);
            }
            criteria.andDirectoryIdIn(values);
    }

    public List<FileMetaVO> queryRecent(int top,String fileType) {
        List<FileMetaVO> list = new ArrayList<>();
        try {
            FileMetaDOExample example = new FileMetaDOExample();
            example.setTop(top);
            example.setOrderByClause("FILE_MODIFY_TIME DESC");
            Calendar calendar= Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(calendar.DAY_OF_MONTH,-1);
            FileMetaDOExample.Criteria criteria = example.createCriteria().andFileModifyTimeGreaterThan(calendar.getTime());
            if(!"ALL".equals(fileType)) criteria.andFileTypeEqualTo(FileUtil.getFileCode(fileType));
            List<FileMetaDO> temList= fileMetaDOMapper.selectByExample(example);
            for(FileMetaDO bean:temList){
                FileMetaVO fileMetaVO = bean.toVO();
                fileMetaVO.setPath(directoryService.queryPathById(bean.getDirectoryId()));
                fileMetaVO.setRemote(indexService.queryById(bean.getIndexId()).getIsRemote());
                list.add(fileMetaVO);
            }
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
        return list;
    }
    @Override
    public FileMetaVO queryById(String id) {
        FileMetaVO result = null;
        FileMetaDO fileMetaDO = fileMetaDOMapper.selectByPrimaryKey(id);
        if(fileMetaDO!=null){
            result = fileMetaDO.toVO();
            result.setPath(directoryService.queryPathById(fileMetaDO.getDirectoryId()));
            result.setRemote(indexService.queryById(fileMetaDO.getIndexId()).getIsRemote());
        }
        return result;
    }

    @Override
    public List<FileMetaVO> queryByDirectoryId(Integer directoryId) {
        List<FileMetaVO> list = new ArrayList<>();
        FileMetaDOExample example = new FileMetaDOExample();
        example.createCriteria().andDirectoryIdEqualTo(directoryId);
        List<FileMetaDO> temList = fileMetaDOMapper.selectByExample(example);
        for(FileMetaDO bean:temList){
            FileMetaVO fileMetaVO = bean.toVO();
            fileMetaVO.setPath(directoryService.queryPathById(bean.getDirectoryId()));
            fileMetaVO.setRemote(indexService.queryById(bean.getIndexId()).getIsRemote());
            list.add(fileMetaVO);
        }
        return list;
    }

    @Override
    public long countByIndexId(short id) {
        FileMetaDOExample example = new FileMetaDOExample();
        example.createCriteria().andIndexIdEqualTo(id);
        return fileMetaDOMapper.countByExample(example);
    }
    @Override
    public void addNX(FileMeta record,boolean immediately) {
        try {
            Integer c = fileMetaDOMapper.existByPrimaryKey(record.getSignature());
            if(c==null) add(record,immediately);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
    @Override
    public void add(FileMeta record,boolean immediately) {
        FileMetaDO fileMetaDO = record.toDO();
        try {
            Integer id = directoryService.queryIdByPath(record.getPath());
            //path不存在,新增文件夹
            if(id==null){
                Directory directory = new Directory(fileMetaDO.getIndexId(),record.getPath(),new Date());
                directoryService.insertRecord(directory);
                id = directory.getId();
            }
            fileMetaDO.setDirectoryId(id);
            if(!bufferList.contains(fileMetaDO)){
                bufferList.add(fileMetaDO);
            }
            long now = System.currentTimeMillis();
            if(immediately||bufferList.size()>1000||(now - lastInsertTime)>insertDuration){
                    this.lastInsertTime = System.currentTimeMillis();
                    fileMetaDOMapper.batchInsert(bufferList);
                    bufferList.clear();
             }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
    @Override
    public void removeById(String id){
        try {
            fileMetaDOMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
    @Override
    public void removeByDir(Integer dirId){
        FileMetaDOExample example = new FileMetaDOExample();
        example.createCriteria().andDirectoryIdEqualTo(dirId);
        try {
            fileMetaDOMapper.deleteByExample(example);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
    @Override
    public void removeByDirs(List<Integer> dirIds){
        FileMetaDOExample example = new FileMetaDOExample();
        example.createCriteria().andDirectoryIdIn(dirIds);
        try {
            fileMetaDOMapper.deleteByExample(example);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
    @Override
    public void removeByIndex(Short indexId){
        FileMetaDOExample example = new FileMetaDOExample();
        example.createCriteria().andIndexIdEqualTo(indexId);
        fileMetaDOMapper.deleteByExample(example);
    }
    @Override
    public void update(FileMeta example){
        try {
            FileMetaDO fileMetaDO = new FileMetaDO();
            fileMetaDO.setId(example.getSignature());
            fileMetaDO.setFileModifyTime(example.getLastModifyTime());
            fileMetaDO.setFileSize(example.getFileSize());
            fileMetaDOMapper.updateByPrimaryKeySelective(fileMetaDO);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }
}
