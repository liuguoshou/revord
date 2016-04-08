package com.recycling.dao.message;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Repository;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecMessage;
import com.recycling.common.enums.PushStatus;
import com.recycling.common.exception.StaleObjectStateException;
import com.recycling.common.util.EnumUtil;
import com.recycling.common.util.Pager;
import com.recycling.common.util.StringUtil;

/**
 * Description : 用户发单请求dao实现 <br/>
 * Copyright : Copyright (c) 2008- 2015 All rights reserved. <br/>
 * Created Time : 2015-3-17 下午6:14:34 <br/>
 * 
 * @author XiaoXian Xu 
 * @version 1.0
 */
@Repository("recMessageDao")
public class RecMessageDaoImpl extends GenericDaoImpl<Long, RecMessage> implements	RecMessageDao {
	
	private static final String INSERT_SQL="insert into rec_message(user_id,trxorder_id,region_id,collect_id,push_status,create_date,update_date,version) values(?,?,?,?,?,?,?,?) ";
	private static final String UPDATE_SQL="update rec_message set user_id=?,trxorder_id=?,region_id=?,collect_id=?,push_status=?,create_date=?,update_date=?,version=? ";
	private static final String QUERY_SQL="select message_id,user_id,trxorder_id,region_id,collect_id,push_status,create_date,update_date,version from rec_message ";

	@Override
	public Long addRecMessage(RecMessage recMessage) {
		getJdbcTemplate().update(INSERT_SQL, 
				recMessage.getUserId(),
				recMessage.getTrxorderId(),
				recMessage.getRegionId(),
				recMessage.getCollectId(),
				recMessage.getPushStatus().name(),
				recMessage.getCreateDate(),
				recMessage.getUpdateDate(),
				recMessage.getVersion()
		);
		return getLastInsertId();
	}

	@Override
	public boolean updateRecMessage(RecMessage recMessage) throws StaleObjectStateException {
		String sql = UPDATE_SQL +" where message_id=? and version=? ";
		int result =getJdbcTemplate().update(sql, 
				recMessage.getUserId(),
				recMessage.getTrxorderId(),
				recMessage.getRegionId(),
				recMessage.getCollectId(),
				recMessage.getPushStatus().name(),
				recMessage.getCreateDate(),
				recMessage.getUpdateDate(),
				recMessage.getVersion()+1,
				recMessage.getMessageId(),
				recMessage.getVersion()
		);
		if(result <= 0){
			throw new StaleObjectStateException(StaleObjectStateException.OPTIMISTIC_LOCK_ERROR);
		}
		return true;
	}

	@Override
	public RecMessage getByMessageId(Long messageId) {
		String sql = QUERY_SQL +" where message_id = ? ";
		List<RecMessage> messageList = getJdbcTemplate().query(sql, new RowMapperImpl(),messageId);
		if(null != messageList && messageList.size() > 0){
			return messageList.get(0);
		}else{
			return null;
		}
	}
	
	 protected class RowMapperImpl implements ParameterizedRowMapper<RecMessage> {
        public RecMessage mapRow(ResultSet rs, int num) throws SQLException {
        	RecMessage recMessage = new RecMessage();
        	recMessage.setMessageId(rs.getLong("message_id"));
        	recMessage.setUserId(rs.getLong("user_id"));
        	recMessage.setTrxorderId(rs.getLong("trxorder_id"));
        	recMessage.setRegionId(rs.getLong("region_id"));
        	recMessage.setCollectId(rs.getLong("collect_id"));
        	recMessage.setPushStatus(EnumUtil.transStringToEnum(PushStatus.class, rs.getString("push_status")));
        	recMessage.setCreateDate(rs.getTimestamp("create_date"));
        	recMessage.setUpdateDate(rs.getTimestamp("update_date"));
        	recMessage.setVersion(rs.getLong("version"));
            return recMessage;
        }
    }

	@Override
	public List<RecMessage> getMesssageList(
				Set<Long> regionIdSet,Long collectId,String startDate,String endDate,Pager pager) {
		if(null == regionIdSet || regionIdSet.isEmpty() || null == collectId){
			return Collections.emptyList();
		}
		String regionIds = StringUtil.arrayToString(regionIdSet.toArray(), ",");
		String sql = QUERY_SQL +" where region_id in("+regionIds+")";
				   sql +=" and (collect_id = ? or collect_id =0) ";
				   sql +=" and create_date > ? and create_date <= ? ";
				   sql +=" order by create_date desc ";
		if(null != pager && pager.getPageSize() > 0){
			sql += " limit "+pager.getStartRow()+","+pager.getPageSize();
		}
		return getJdbcTemplate().query(sql, new RowMapperImpl(),collectId,startDate,endDate);
	}

	@Override
	public int queryUserUnComplateMsg(Long userId,String startDate,String endDate) {
		String sql = "SELECT COUNT(*) FROM rec_message msg"
								+" LEFT JOIN rec_trxorder trx ON trx.trxorder_id = msg.trxorder_id "
								+" WHERE trx.trx_status IN('INIT','PROCESSING','CONFIRM') "
								+" AND msg.user_id = ? and msg.create_date > ? and msg.create_date <= ? ";
		return getJdbcTemplate().queryForObject(sql, Integer.class, userId,startDate,endDate);
	}


    /**
     * 查询前一天乞丐未接单的订单及消息
     * @param fromDate
     * @param toDate
     * @return
     */
    @Override
    public Map<String, List<Long>> queryInitMessageList(String fromDate, String toDate) {
        String sql=" select message_id,trxorder_id from rec_message where push_status='INIT' and  create_date >= ? and create_date <=? ";
        List<Map<String,Object>> listmap=this.getJdbcTemplate().queryForList(sql, fromDate, toDate);
        if(listmap == null || listmap.size() ==0){
            return null;
        }
        List<Long> messageList=new ArrayList<Long>();
        List<Long> orderList=new ArrayList<Long>();
        for (Map<String,Object> map:listmap){
            Long message_id= (Long) map.get("message_id");
            Long order_id= (Long) map.get("trxorder_id");
            messageList.add(message_id);
            orderList.add(order_id);
        }
        Map<String, List<Long>> map=new HashMap<String, List<Long>>();
        map.put("messageList",messageList);
        map.put("orderList",orderList);
        return map;
    }


    /**
     * 重置消息状态
     * @param messageIds
     * @return
     */
    @Override
    public Integer resetMessageStatus(String messageIds) {
        String sql=" update rec_message set  push_status = 'CANCEL'  where message_id in ("+messageIds+")";
        int total=this.getJdbcTemplate().update(sql);
        return total;
    }

    @Override
	public int getMesssageCount(Set<Long> regionIdSet, Long collectId,String startDate, String endDate) {
		if(null == regionIdSet || regionIdSet.isEmpty() || null == collectId){
			return 0;
		}
		String regionIds = StringUtil.arrayToString(regionIdSet.toArray(), ",");
		String sql = "select count(*) from rec_message where region_id in("+regionIds+")";
				   sql +=" and (collect_id = ? or collect_id =0) ";
				   sql +=" and create_date > ? and create_date <= ? ";
		return getJdbcTemplate().queryForObject(sql,Integer.class,collectId,startDate,endDate);
	}

	@Override
	public RecMessage getByTrxorderId(Long trxorderId) {
		String sql = QUERY_SQL +" where trxorder_id = ? ";
		List<RecMessage> messageList = getJdbcTemplate().query(sql, new RowMapperImpl(),trxorderId);
		if(null != messageList && messageList.size() > 0){
			return messageList.get(0);
		}else{
			return null;
		}
	}
}
