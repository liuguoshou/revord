package com.recycling.admin.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title：菜单实体
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class AdminMenus implements Serializable {

	private static final long serialVersionUID = -8918705479696509341L;

	private Long menuId;

	private String menuName;// 菜单名称

	private String menuUrl;// 菜单URL

	private Integer isValid;// 是否有效

	private Long parentId;// 父级别id
	
	private Integer isDisable;//是否可见

    private String menuIcon;

	public Integer getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof AdminMenus))
			return false;
		AdminMenus am = (AdminMenus) obj;

		return this.getMenuUrl().equals(am.getMenuUrl());
	}

	@Override
	public int hashCode() {
		return this.getMenuUrl().hashCode();
	}

	private Integer menuDisplay;// 菜单是否显示

	private List<AdminMenus> listSubMenu;// 子级别菜单

	public Integer getMenuDisplay() {
		return menuDisplay;
	}

	public void setMenuDisplay(Integer menuDisplay) {
		this.menuDisplay = menuDisplay;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public List<AdminMenus> getListSubMenu() {
		return listSubMenu;
	}

	public void setListSubMenu(List<AdminMenus> listSubMenu) {
		this.listSubMenu = listSubMenu;
	}

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
}
