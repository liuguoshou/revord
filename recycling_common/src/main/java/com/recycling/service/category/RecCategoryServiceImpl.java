package com.recycling.service.category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recycling.common.constants.RecConstants;
import com.recycling.common.entity.RecCategory;
import com.recycling.common.service.MemCacheService;
import com.recycling.common.service.MemCacheServiceImpl;
import com.recycling.dao.category.RecCategoryDao;

@Service("recCategoryService")
public class RecCategoryServiceImpl implements RecCategoryService{

	MemCacheService mem = MemCacheServiceImpl.getInstance();
	
	@Autowired
	private RecCategoryDao recCategoryDao;
	
	@Override
	public Long addRecCategory(RecCategory recCategory) {
		return recCategoryDao.addRecCategory(recCategory);
	}

	@Override
	public boolean updateRecCategory(RecCategory recCategory) {
		return recCategoryDao.updateRecCategory(recCategory);
	}

	@Override
	public RecCategory getByCategoryId(Long categoryId) {
		RecCategory recCategory = (RecCategory)mem.get(RecConstants.REC_CATEGORY_INFO+categoryId);
		if(null == recCategory){
			recCategory = recCategoryDao.getByCategoryId(categoryId);
			if(null != recCategory){
				mem.set(RecConstants.REC_CATEGORY_INFO+categoryId, recCategory,RecConstants.cache_time);
			}
		}
		return recCategory;
	}

	@Override
	public List<RecCategory> getByParentId(Long parentId) {
//		List<RecCategory> recCategoryList = (List<RecCategory>)mem.get(RecConstants.REC_CATEGORY_LIST+parentId);
//		if(null == recCategoryList){
//			recCategoryList = recCategoryDao.getByParentId(parentId);
//			if(null != recCategoryList){
//				mem.set(RecConstants.REC_CATEGORY_LIST+parentId, recCategoryList,RecConstants.cache_time);
//			}
//		}
//		return recCategoryList;
		return recCategoryDao.getByParentId(parentId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long, RecCategory> getAllCategoryInfo() {
		Map<Long,RecCategory> recCategoryMap = (Map<Long,RecCategory>)mem.get(RecConstants.REC_ALL_CATEGORY_MAP);
		if(null == recCategoryMap){
			recCategoryMap = new HashMap<Long, RecCategory>();
			List<RecCategory> recCategoryList = recCategoryDao.getAllCategoryInfo();
			if(null != recCategoryList){
				for (RecCategory recCategory : recCategoryList) {
					recCategoryMap.put(recCategory.getCategoryId(), recCategory);
				}
				mem.set(RecConstants.REC_ALL_CATEGORY_MAP, recCategoryMap,RecConstants.cache_time);
			}
		}
		return recCategoryMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<Long, List<RecCategory>> getCategoryRelationMap() {
		Map<Long,List<RecCategory>> recCategoryMap = (Map<Long,List<RecCategory>>)mem.get(RecConstants.REC_CATEGORY_RELATION_MAP);
		if(null == recCategoryMap){
			recCategoryMap = new HashMap<Long,List<RecCategory>>();
			List<RecCategory> recCategoryList = recCategoryDao.getAllCategoryInfo();
			if(null != recCategoryList && recCategoryList.size() > 0){
				for (RecCategory recCategory : recCategoryList) {
					if(recCategoryMap.containsKey(recCategory.getParentId())){
						recCategoryMap.get(recCategory.getParentId()).add(recCategory);
					}else{
						List<RecCategory> categoryList = new ArrayList<RecCategory>();
						categoryList.add(recCategory);
						recCategoryMap.put(recCategory.getParentId(), categoryList);
					}
				}
				mem.set(RecConstants.REC_CATEGORY_RELATION_MAP, recCategoryMap,RecConstants.cache_time);
			}
		}
		return recCategoryMap;
	}

}
