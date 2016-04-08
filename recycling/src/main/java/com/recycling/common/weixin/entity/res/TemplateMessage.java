package com.recycling.common.weixin.entity.res;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:TemplateMessage.java
 * @Description:模板消息实体
 * @author:xu.he
 * @version:v1.0
 */
public class TemplateMessage {


    private String touser;

    private String template_id;

    private String url;

    private String topcolor;

    private Map<String,TemplateMessageValue> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public Map<String, TemplateMessageValue> getData() {
        return data;
    }

    public void setData(Map<String, TemplateMessageValue> data) {
        this.data = data;
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

        System.out.println(JSON.toJSONString(templateMessage));

    }
}
