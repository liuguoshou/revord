package com.recycling.common.weixin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.recycling.common.constants.RecConstants;
import com.recycling.common.util.DomainUtils;
import com.recycling.common.weixin.entity.menu.ToolbarMenu;
import com.recycling.common.weixin.entity.res.TemplateMessage;
import com.recycling.common.weixin.entity.res.TemplateMessageValue;
import com.recycling.service.weixin.CommonUtils;
import com.recycling.service.weixin.WeiXinUtil;
import com.recycling.service.weixin.WeixinToken;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:TemplateMessageUtil.java
 * @Description:模板消息工具类
 * @author:xu.he
 * @version:v1.0
 */
public class TemplateMessageUtil {

    private static Logger logger=Logger.getLogger(TemplateMessageUtil.class);

    /**
     * 用户下单通知
     */
    public static String ORDER_INIT_TEMPLATE_ID="1het86V4jMNthrHpq2Nw3jyNbGW31Iu3oaRWiCxPskQ";

    /**
     * 乞丐接单通知
     */
    public static String ORDER_PROCESSING_TEMPLATE_ID="RNpsnv58HmS51OTCL7jsy3FSrzRzroPzu-937VRNNUE";

    /**
     *用户驳回订单
     */
    public static String ORDER_RETRUNBACK_TEMPLATE_ID="FAnZJCQ_4Hnl_j5orqICcTxi9Uj3jpAVwyE_XDNIoaI";

    /**
     * 乞丐编辑完成等待确认
     */
    public static String ORDER_CONFIRM_TEMPLATE_ID="rhRWBuRbIxjAwB3uQ8WQA1ttbHEGjfjmUAw-lwgAr1A";

    /**
     * 用户确认订单
     */
    public static String ORDER_SUCCESS_TEMPLATE_ID="kt16CnEmViXd0JdVQsgs6808Oa_H3aXGiBJRQ-DR3UI";

    /**
     * 用户取消订单
     */
    public static String ORDER_CANCEL_TEMPLATE_ID="sv3vE3MKeqDSfc1lvMJijDCriTPcCh6vtZSWxIKslQY";

    //发送模板消息接口
    public static String TEMPLATE_MESSAGE_SEND="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";


    /**
     * 发送短信模板
     * @param jsonMenu
     * @return
     */
    public static String sendTemplateMessage(String jsonMenu) {

        String resultStr = "";
        // 调用接口获取token
        WeixinToken wt = CommonUtils.getTokenByAppid(RecConstants.getWeixinAppkey(), RecConstants.getWeixinSecret());
        String accessToken= wt.getToken();
        if (accessToken != null) {
            // 调用接口创建菜单
            int result = sendTemplateMessage(jsonMenu, accessToken);
            logger.info("===========sendTemplateMessage==========:"+jsonMenu+"===========status:"+result+"===========");
            // 判断菜单创建结果
            if (0 == result) {
                resultStr = "发送成功！";
            } else {
                resultStr = "发送失败，错误码：" + result;
            }
        }

        return resultStr;
    }


    public static int sendTemplateMessage(String jsonStr,String accessToken){
        int result = 0;
        String url=TEMPLATE_MESSAGE_SEND.replace("ACCESS_TOKEN",accessToken);
        String json=WeiXinUtil.httpRequest(url,"POST",jsonStr);
        JSONObject jsonObject= JSON.parseObject(json);
        if (null != jsonObject) {
            if (0 != jsonObject.getIntValue("errcode")) {
                result = jsonObject.getIntValue("errcode");
            }
        }
        return result;
    }

    /**
     * 获得发送模板json字符串
     * @param openId
     * @param templateId
     * @param url
     * @param msgMap
     * @return
     */
    public static String getTemplateMessageJsonStr(String openId,String templateId,String url,Map<String,TemplateMessageValue> msgMap){
//        Map<String,TemplateMessageValue> msgMap=new HashMap<String, TemplateMessageValue>();
//        msgMap.put("beggerName",new TemplateMessageValue("土豆","#173177"));
//        msgMap.put("userName",new TemplateMessageValue("小徐","#173177"));
//        msgMap.put("userMobile",new TemplateMessageValue("18600198020","#173177"));
        TemplateMessage templateMessage=new TemplateMessage();
        templateMessage.setTouser(openId);
        templateMessage.setTemplate_id(templateId);
        templateMessage.setTopcolor("#FF0000");
        templateMessage.setUrl(url);
        templateMessage.setData(msgMap);
        return JSON.toJSONString(templateMessage);
    }

    public static void main(String []args){
        Map<String,TemplateMessageValue> msgMap=new HashMap<String, TemplateMessageValue>();
        msgMap.put("beggerName",new TemplateMessageValue("土豆","#173177"));
        msgMap.put("userName",new TemplateMessageValue("小徐","#173177"));
        msgMap.put("userMobile",new TemplateMessageValue("18600198020","#173177"));
        TemplateMessage templateMessage=new TemplateMessage();
        templateMessage.setTouser("oNmQit66-LQqqa3aATS1W8Boofn8");
        templateMessage.setTemplate_id("i76-X1NPdSMnIhJ4Q8ubwQyqwacaS7ej7yLPjE-D6mk");
        templateMessage.setTopcolor("#FF0000");
        templateMessage.setUrl("http://121.41.57.46:8090/weixin/su.do?goType=user_index");
        templateMessage.setData(msgMap);
        WeixinToken wt = CommonUtils.getTokenByAppid("wx26922fbac7a743c6", "995ec408815bf2bb1b18f7481fe29bdf");
        String accessToken= wt.getToken();

        TemplateMessageUtil.sendTemplateMessage(JSON.toJSONString(templateMessage),accessToken);

    }



}
