package com.recycling.admin.user.service;

import com.recycling.admin.region.dao.RecyclingAdminRegionDao;
import com.recycling.admin.user.dao.RecyclingAdminUserDao;
import com.recycling.admin.user.querycase.RecyclingAdminUserQueryCase;
import com.recycling.common.entity.RecRegion;
import com.recycling.common.entity.RecUser;
import com.recycling.common.util.CommonUtils;
import com.recycling.dao.region.RecRegionDao;
import com.recycling.dao.user.RecUserDao;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.Region;

import java.util.List;

/**
 * @Title:RecyclingAdminUserServiceImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Service("recyclingAdminUserService")
public class RecyclingAdminUserServiceImpl implements RecyclingAdminUserService {

    @Autowired
    private RecyclingAdminUserDao recyclingAdminUserDao;

    @Autowired
    private RecUserDao recUserDao;

    @Autowired
    private RecRegionDao recRegionDao;
    
    @Autowired
    private RecyclingAdminRegionDao recyclingAdminRegionDao;

    /**
     * 查询用户列表
     * @param queryCase
     * @return
     */
    @Override
    public List<RecUser> queryRecyclingUser(RecyclingAdminUserQueryCase queryCase) {
    	String area_parent_id = queryCase.getArea_parent_id();
    	if(StringUtils.isNotBlank( area_parent_id)){
    		area_parent_id = recyclingAdminRegionDao.getParentIds(Long.parseLong(area_parent_id));
    		queryCase.setArea_parent_id(area_parent_id);
    	}
    	
    	
        List<Long> idList=recyclingAdminUserDao.queryRecyclingUser(queryCase);
        if(idList == null || idList.size() == 0){
            return null;
        }
        String ids= CommonUtils.listToString(idList);
        List<RecUser> recUserList=recUserDao.getUserByIds(ids);

        for (RecUser recUser:recUserList){
            RecRegion region=recRegionDao.getByRegionId(recUser.getRegionId(), 1, 1);
            recUser.setRegion(region);
        }
        return recUserList;
    }

    /**
     * 查看用户详情
     * @param user_id
     * @return
     */
    @Override
    public RecUser getRecyclingUserById(Long user_id) {
        RecUser recUser=recUserDao.getByUserId(user_id);
        RecRegion region=recRegionDao.getByRegionId(recUser.getRegionId(), 1, 1);
       // RecRegion parent_region=recRegionDao.getByRegionId(region.getParentId(),0,1);
       // region.setParentRegion(parent_region);
        recUser.setRegion(region);
        return recUser;
    }
}
