package com.recycling.common.weixin.entity.res;

/**
 * @Title:TemplateMessageValue.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public class TemplateMessageValue {

    private String value;

    private String color;


    public TemplateMessageValue(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
