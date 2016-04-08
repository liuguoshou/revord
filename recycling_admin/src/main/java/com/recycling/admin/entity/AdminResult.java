package com.recycling.admin.entity;

import java.io.Serializable;

/**
 * @Title:AdminResult.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public class AdminResult implements Serializable{
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private String type;
    private String msg;

    public AdminResult() {
        super();
    }

    public AdminResult(String type, String msg) {
        super();
        this.type = type;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
