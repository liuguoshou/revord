package com.recycling.dao.user;

import com.recycling.common.ds.GenericDaoImpl;
import com.recycling.common.entity.RecWeixinUser;
import org.springframework.stereotype.Component;

/**
 * @Title:RecUserWeixinDaoImpl.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
@Component("recUserWeixinDao")
public class RecWeixinUserDaoImpl extends GenericDaoImpl implements RecWeixinUserDao {

    /**
     * 添加用户微信信息
     * @param recWeixinUser
     * @return
     */
    @Override
    public Long addRecUserWeixin(RecWeixinUser recWeixinUser) {
        String sql=" insert into rec_weixin_user (weixin_username,weixin_openid,weixin_headimgurl,weixin_usercity,user_id) values(?,?,?,?,?) ";
        this.getJdbcTemplate().update(sql,recWeixinUser.getWeixinNickName(),recWeixinUser.getOpenId(),recWeixinUser.getWeixinHeadImgUrl(),
                recWeixinUser.getCity(),recWeixinUser.getUserId()
        );
        return getLastInsertId();
    }
}
