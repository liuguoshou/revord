package com.recycling.admin.pauper.service;

import com.recycling.admin.auth.dao.AdminUserDao;
import com.recycling.admin.entity.AdminUserArea;
import com.recycling.admin.pauper.querycase.RecyclingAdminPauperQueryCase;
import com.recycling.admin.region.dao.RecyclingAdminRegionDao;
import com.recycling.admin.system.dao.AdminSystemUserDao;
import com.recycling.common.entity.RecBeggar;
import com.recycling.common.entity.RecBeggarAccount;
import com.recycling.common.entity.RecBeggarAddress;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.enums.AccountStatus;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.CommonUtils;
import com.recycling.dao.beggar.RecBeggarAccountDao;
import com.recycling.dao.beggar.RecBeggarAddressDao;
import com.recycling.dao.beggar.RecBeggarDao;
import com.recycling.dao.region.RecRegionDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Title:RecyclingAdminPauperServiceImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Service("recyclingAdminPauperService")
public class RecyclingAdminPauperServiceImpl implements RecyclingAdminPauperService {

    @Autowired
    private RecBeggarDao recBeggarDao;

    @Autowired
    private RecRegionDao recRegionDao;

    @Autowired
    private RecBeggarAddressDao recBeggarAddressDao;

    @Autowired
    private RecBeggarAccountDao recBeggarAccountDao;
    
    @Autowired
    private RecyclingAdminRegionDao recyclingAdminRegionDao;
    
    @Autowired
    private AdminUserDao adminUserDao;

    /**
     * 查询乞丐列表
     * @param queryCase
     * @return
     */
    @Override
    public Map<String,Object> queryRecyclingPauper(RecyclingAdminPauperQueryCase queryCase) {
    	
    	
    	
    	
    	String region_parent_ids = queryCase.getRegion_parent_id();
    	if(StringUtils.isNotBlank(region_parent_ids)){
    		region_parent_ids = recyclingAdminRegionDao.getParentIds(Long.parseLong(region_parent_ids));
    	}
    	long tt = queryCase.getUserId();
    	int it= (int) tt;
    	//用户权限管理 
    	AdminUserArea uaserArea =adminUserDao.queryAdminUserArea(it);
    	
    	String userAreaIds="";
    	if(uaserArea.getAreaType()==5){//小区Id
    		StringBuffer sbf = new StringBuffer();
    		String [] ids = uaserArea.getAreaIds().split(",");
    		sbf.append("");
    		for (String id : ids) {
    			sbf.append(id);
    			sbf.append(",");
			}
    		userAreaIds = sbf.substring(0, sbf.length()-1);
    	}else{							//其它
    		userAreaIds=recyclingAdminRegionDao.getParentIds(Long.parseLong(uaserArea.getAreaIds()));
    	}
    	
//        Map<String,Object> listmap=recBeggarDao.queryBeggerList(region_parent_ids, queryCase.getRegion_id(), queryCase.getPager().getStartRow(), queryCase.getPager().getPageSize());
    	Map<String,Object> listmap=recBeggarDao.queryBeggerList(region_parent_ids,uaserArea.getAreaType(),userAreaIds  ,queryCase.getRegion_id(), queryCase.getPager().getStartRow(), queryCase.getPager().getPageSize());

        if(listmap!=null && listmap.size()>0){
            List<Map<String,Object>> objList= (List<Map<String, Object>>) listmap.get("result");
            Map<Long,String> parent_regionCnName=new HashMap<Long, String>();
            Set<Long> parent_ids=new HashSet<Long>();
            for (Map<String,Object> map:objList){
                Long pid= (Long) map.get("parent_id");
                parent_ids.add(pid);
            }
           /* if(parent_ids != null && parent_ids.size() > 0){
                String ids=CommonUtils.listToString(new ArrayList<Long>(parent_ids));
                parent_regionCnName=recRegionDao.getParentRegionCnName(ids);
                for (Map<String,Object> map:objList){
                    Long parent_id= (Long) map.get("parent_id");
                    String regname=parent_regionCnName.get(parent_id);
                    map.put("parent_regionCnName",regname);
                }
            } */
        }else{
            listmap=new HashMap<String, Object>();
            listmap.put("result",null);
            listmap.put("totalCount",0);
        }
        return listmap;
    }

    /**
     * 添加乞丐
     * @param recBeggar
     * @return
     */
    @Override
    public Boolean addRecyclingPauper(RecBeggar recBeggar,String region_ids  ,Long area_id) {
        Boolean flag=true;
        Long pauper_Id=recBeggarDao.addBeggar(recBeggar);
        flag=addPauperAddress(pauper_Id,region_ids ,area_id);
        RecBeggarAccount account = new RecBeggarAccount();
        account.setBeggarId(pauper_Id);
        account.setAccountStatus(AccountStatus.ACTIVE);
        recBeggarAccountDao.addAccount(account);
        if(pauper_Id==null){
            flag=false;
        }
        return flag;
    }

    /**
     * 修改乞丐
     * @param recBeggar
     * @return
     */
    @Override
    public Boolean updateRecyclingPauper(RecBeggar recBeggar,String region_ids,Long area_id) {
        Boolean flag=true;
        try {
            recBeggarDao.updateBeggarInfo(recBeggar);
            //删除相关负责小区
            flag=recBeggarAddressDao.deleteAddressByBeggarId(recBeggar.getBeggarId());
            flag=addPauperAddress(recBeggar.getBeggarId(),region_ids,area_id);
        } catch (StaleObjectStateException e) {
            e.printStackTrace();
            flag=false;
        }
        return flag;
    }

    /**
     * 批量添加乞丐负责小区
     * @param pauper_Id
     * @param region_ids
     * @return
     */
    private Boolean addPauperAddress(Long pauper_Id,String region_ids ,Long area_id){
        Boolean flag=true;
        if(region_ids.length()>0){
            String[] region_id_arr= new String[]{};
            if(region_ids.indexOf(",")>0){
                region_id_arr=region_ids.split(",");
            }else{
                region_id_arr=new String[]{region_ids};
            }
            for (String region_id : region_id_arr){
                RecBeggarAddress address=new RecBeggarAddress();
                address.setAreaId(area_id);
                address.setCreateDate(new Date());
                address.setUpdateDate(new Date());
                address.setVersion(0L);
                address.setBeggarId(pauper_Id);
                address.setRegionId(Long.valueOf(region_id));
                address.setAddress("");
                Long addAddress_id=recBeggarAddressDao.addAddress(address);
                if(addAddress_id<=0){
                    flag=false;
                }
            }
        }
        return flag;
    }

    /**
     * 修改乞丐状态
     * @param beggar_id
     * @param status
     * @return
     */
    @Override
    public Boolean updateStatusRecyclingPauper(Long beggar_id, int status) {
        Boolean flag=true;
        if(status==0){
            flag=recBeggarDao.updateStatusUser(beggar_id,"FROZEN");
        }else{
            flag=recBeggarDao.updateStatusUser(beggar_id,"ACTIVE");
        }
        return flag;
    }

    /**
     * 查询乞丐详情
     * @param beggar_id
     * @return
     */
    @Override
    public RecBeggar getRecyclingPauperById(Long beggar_id) {
        RecBeggar recBeggar=recBeggarDao.getById(beggar_id);
        List<RecBeggarAddress> addressList=recBeggarAddressDao.getByBeggarId(recBeggar.getBeggarId());
        List<Long> idList=new ArrayList<Long>();
        for (RecBeggarAddress address:addressList){
            idList.add(address.getRegionId());
        }
        String ids=CommonUtils.listToString(idList);
        Long region_id=addressList.get(0).getRegionId();
        RecRegion recRegion=recRegionDao.getByRegionId(region_id, 0, 1);
        recBeggar.setAddressList(addressList);
        recBeggar.setRegionids(ids);
        recBeggar.setRegion(recRegion);
        return recBeggar;
    }
}
