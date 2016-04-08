package com.recycling.admin.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Title:AdminWeixinMenu.java
 * @Description:TODO
 * @author:xu.he
 * @version:v1.0
 */
public class AdminWeixinMenu implements Serializable {


    /**
     * 微信菜单ID
     */
    private int id;
    /**
     * 微信菜单类型：1、click 2、view
     */
    private String type;
    /**
     * 微信菜单名称
     */
    private String name;
    /**
     * 微信菜单click事件，关键字
     */
    private String key;
    /**
     * 微信菜单view事件，url
     */
    private String url;
    /**
     * 当前菜单所属父级菜单id
     */
    private int pid;
    /**
     * 所属公众账号标识
     * weixin_test:微信测试账号
     * weixin_subscript:微信订阅号
     * weixin_service:微信服务号
     */
    private String menu_type;
    /**
     * 更新时间
     */
    private Timestamp update_time;

    /**
     * 排序从小到大
     */
    private int sort;
    /**
     * 子菜单集合
     */
    private List<AdminWeixinMenu> sub_button;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPid() {
        return pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<AdminWeixinMenu> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<AdminWeixinMenu> sub_button) {
        this.sub_button = sub_button;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.menu_type = menu_type;
    }
}
