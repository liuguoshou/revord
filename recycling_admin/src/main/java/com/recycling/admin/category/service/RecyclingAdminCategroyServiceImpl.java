package com.recycling.admin.category.service;

import com.recycling.admin.category.dao.RecyclingAdminCategroyDao;
import com.recycling.admin.category.querycase.RecyclingAdminCategroyQueryCase;
import com.recycling.admin.region.querycase.RecyclingAdminRegionQueryCase;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecCategory;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;
import com.recycling.common.util.CommonUtils;
import com.recycling.dao.category.RecCategoryDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title:RecyclingAdminCategroyServiceImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Service("recyclingAdminCategroyService")
public class RecyclingAdminCategroyServiceImpl implements RecyclingAdminCategroyService {

    @Autowired
    private RecCategoryDao recCategoryDao;

    @Autowired
    private RecyclingAdminCategroyDao recyclingAdminCategroyDao;

    private MemCacheService mem= MemCacheServiceImpl.getInstance();

    /**
     * 查询分类列表
     * @param queryCase
     * @return
     */
    @Override
    public List<RecCategory> queryRecyclingCategroy(RecyclingAdminCategroyQueryCase queryCase) {

        if(StringUtils.isNotBlank(queryCase.getParent_category_id())){
            List<RecCategory> categoryList=recCategoryDao.getByParentId(Long.valueOf(queryCase.getParent_category_id()));
            List<Long> parentIds=new ArrayList<Long>();
            if(categoryList!=null && categoryList.size()>0){
                for (RecCategory recCategory:categoryList){
                    parentIds.add(recCategory.getCategoryId());
                }
                queryCase.setSub_categroy_id(CommonUtils.listToString(parentIds));
            }

        }

        List<Long> idList=recyclingAdminCategroyDao.queryRecyclingCategroy(queryCase);
        if(idList == null|| idList.size() == 0){
            return null;
        }
        String ids= CommonUtils.listToString(idList);
        List<RecCategory> recCategoryList=recCategoryDao.queryCategroyByIds(ids);
        return recCategoryList;
    }


    /**
     * 查询所有父分类信息
     * @return
     */
    @Override
    public List<RecCategory> queryRecyclingParentCategroy() {
        return recCategoryDao.getAllParentCategroy();
    }

    /**
     * 查询分类详情
     * @param categroy_id
     * @return
     */
    @Override
    public RecCategory getRecyclingCategroyById(Long categroy_id) {
        return recCategoryDao.getByCategoryId(categroy_id);
    }

    /**
     * 保存分类
     * @param category
     * @return
     */
    @Override
    public Boolean addRecyclingCategroy(RecCategory category) {
        Long categroy_id=recCategoryDao.addRecCategory(category);
        Boolean flag=true;
        if(categroy_id == null){
            flag=false;
        }
        mem.remove(RecConstants.REC_ALL_CATEGORY_MAP);
        return flag;
    }

    /**
     * 更新分类
     * @param category
     * @return
     */
    @Override
    public Boolean updateRecyclingCategroy(RecCategory category) {
        Boolean flag=recCategoryDao.updateRecCategory(category);
        mem.remove(RecConstants.REC_ALL_CATEGORY_MAP);
        return flag;
    }

    /**
     * 根据分类ID子分类
     * @param categroy_id
     * @return
     */
    @Override
    public List<RecCategory> queryRecyclingCategroySubList(Long categroy_id) {
        return recCategoryDao.getByParentId(categroy_id);
    }
}
