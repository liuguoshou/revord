package com.recycling.common.weixin.entity.menu;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title:ToobarMenu.java
 * @Description:TODO
 * @author:ye.tian
 * @version:v1.0
 */
public class ToolbarMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7063154638259802740L;

	private List<MenuModel> button;

	public List<MenuModel> getButton() {
		return button;
	}

	public void setButton(List<MenuModel> button) {
		this.button = button;
	}

	public static String jsonNewMenu() {
		
		
		ToolbarMenu tm = new ToolbarMenu();
		
		List<MenuModel> listParent=new ArrayList<MenuModel>();
		
		
		List<MenuModel> listParent1 = new ArrayList<MenuModel>();
		
		
		MenuModel m2=new MenuModel();
		
		
		MenuModel mmFans4 = new MenuModel();
		mmFans4.setName("纪念珠宝");
		mmFans4.setType("view");
		mmFans4.setUrl("http://wd.koudai.com/s/211022518/");
		listParent1.add(mmFans4);
		

		
		MenuModel mmFans3 = new MenuModel();
		mmFans3.setName("官网登录");
		mmFans3.setType("view");
//		mmFans3.setUrl("http://121.41.57.46/forwardTo.do?type=wxlogin");
		mmFans3.setUrl("http://www.nowandthen.com.cn/forwardTo.do?type=wxlogin");
		listParent1.add(mmFans3);
		
		MenuModel mmFans = new MenuModel();
		mmFans.setName("即刻体验");
		mmFans.setType("view");
//		mmFans.setUrl("http://121.41.57.46/weixin/su.do?goType=goods_list");
		mmFans.setUrl("http://www.nowandthen.com.cn/weixin/su.do?goType=goods_list");
		listParent1.add(mmFans);
		
//		MenuModel mmFans4 = new MenuModel();
//		mmFans4.setName("清理cookie");
//		mmFans4.setType("view");
//		mmFans4.setUrl("http://121.41.57.46/test/removeWeixinCookie.do");
//		listParent1.add(mmFans4);
		
		MenuModel mmFans2 = new MenuModel();
		mmFans2.setName("我的体验");
		mmFans2.setType("view");
//		mmFans2.setUrl("http://121.41.57.46/weixin/su.do?goType=my_book_order");
		mmFans2.setUrl("http://www.nowandthen.com.cn/weixin/su.do?goType=my_book_order");
		listParent1.add(mmFans2);
		
		
		
		m2.setName("记忆宝藏");
		m2.setSub_button(listParent1);
		
		
		
		
		MenuModel m1=new MenuModel();
		m1.setName("此时彼刻");
		List<MenuModel> listParent2=new ArrayList<MenuModel>();
		
//		MenuModel mm5=new MenuModel();
//		mm5.setName("测试分享");
//		mm5.setType("view");
//		mm5.setUrl("http://121.41.57.46/jsp/activity/textfx.jsp");
//		listParent2.add(mm5);
		
		m1.setSub_button(listParent2);
		
		MenuModel mm2=new MenuModel();
		mm2.setName("品牌发布");
		mm2.setType("view");
		mm2.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwMzA2NTQ0Mg==&mid=203282092&idx=1&sn=be98978335fdd0620372767922eccd44#rd");
		listParent2.add(mm2);
		
		
		
		MenuModel mm1=new MenuModel();
		mm1.setName("品牌故事");
		mm1.setType("view");
		mm1.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwMzA2NTQ0Mg==&mid=203283897&idx=1&sn=c68dd96b9aba41be708cf29843f9059e#rd");
		listParent2.add(mm1);
		
	
		
		
	
		
		
		MenuModel m3=new MenuModel();
		m3.setName("热门活动");
		
		List<MenuModel> listParent3=new ArrayList<MenuModel>();
		MenuModel mmFans1 = new MenuModel();
		mmFans1.setName("定制情人节");
		mmFans1.setType("view");
//		mmFans1.setUrl("http://121.41.57.46/weixin/su.do?goType=activity_goods");
		mmFans1.setUrl("http://www.nowandthen.com.cn/weixin/su.do?goType=activity_goods");
		listParent3.add(mmFans1);
		
		
		MenuModel mm3=new MenuModel();
		mm3.setName("买家晒单");
		mm3.setType("view");
		mm3.setUrl("http://mp.weixin.qq.com/s?__biz=MzAwMzA2NTQ0Mg==&mid=203284734&idx=1&sn=5817d9aca0e92918f2d5df249cc7a923#rd");
		listParent3.add(mm3);
		
		
		
		
		
		
		MenuModel mm4=new MenuModel();
		mm4.setName("客服小妞");
		mm4.setType("click");
		mm4.setKey("MENU_KEFU");
		listParent3.add(mm4);
		
		
		m3.setSub_button(listParent3);
		
		
		
		listParent.add(m1);
		listParent.add(m2);
		listParent.add(m3);
		
		tm.setButton(listParent);
		String json = JSON.toJSONString(tm);
		return json;
	}

	public static String jsonMenu() {

		ToolbarMenu tm = new ToolbarMenu();
		List<MenuModel> listParent = new ArrayList<MenuModel>();

		MenuModel mmFans = new MenuModel();
		mmFans.setName("星空间");

		List<MenuModel> listSubMenu_ = new ArrayList<MenuModel>();
		MenuModel subMenu_ = new MenuModel();
		subMenu_.setName("MyLove");
		subMenu_.setType("view");
		String weixinName = "";//Constant.weixinName
		subMenu_.setUrl("http://www.doubifan.com/weixin/su.do?oid="
				+ weixinName + "&goType=go");
		// subMenu_.setUrl("http://115.29.224.66/weixin/su.do?oid="
		// + weixinName + "&goType=go");
		MenuModel subMenu2_ = new MenuModel();
		subMenu2_.setName("明星汇");
		subMenu2_.setType("view");
		subMenu2_.setUrl("http://www.doubifan.com/weixin/su.do?oid="
				+ weixinName + "&goType=main");
		// subMenu2_.setUrl("http://115.29.224.66/weixin/su.do?oid="
		// + weixinName + "&goType=main");

		MenuModel subMenu4_ = new MenuModel();

		subMenu4_.setName("放开阿布让我来");
		subMenu4_.setType("view");
		// subMenu4_.setUrl("http://115.29.224.66/game/kiss.do?weixin_name="+weixinName);
		subMenu4_.setUrl("http://www.doubifan.com/game/kiss.do?weixin_name="
				+ weixinName);

		MenuModel subMenu3_ = new MenuModel();

		subMenu3_.setName("亚纶养成记");
		subMenu3_.setType("view");
		// subMenu3_.setUrl("http://115.29.224.66/game/2048.do?weixin_name="+weixinName);
		subMenu3_.setUrl("http://www.doubifan.com/game/2048.do?weixin_name="
				+ weixinName);

		MenuModel subMenu5_ = new MenuModel();

		subMenu5_.setName("欧巴擦浪嘿");
		subMenu5_.setType("view");
		// subMenu5_.setUrl("http://115.29.224.66/game/memeda.do?weixin_name="+weixinName);
		subMenu5_.setUrl("http://www.doubifan.com/game/memeda.do?weixin_name="
				+ weixinName);

		listSubMenu_.add(subMenu5_);
		listSubMenu_.add(subMenu4_);
		listSubMenu_.add(subMenu3_);
		listSubMenu_.add(subMenu2_);
		listSubMenu_.add(subMenu_);
		mmFans.setSub_button(listSubMenu_);

		MenuModel mmParent = new MenuModel();
		mmParent.setName("豆比秀");

		List<MenuModel> listSubMenu = new ArrayList<MenuModel>();
		MenuModel subMenu = new MenuModel();
		subMenu.setName("秀图");
		subMenu.setType("click");
		subMenu.setKey("CREZY_P_M001001");
		MenuModel subMenu2 = new MenuModel();
		subMenu2.setName("秀声");
		subMenu2.setType("click");
		subMenu2.setKey("CREZY_P_M001002");

		MenuModel subMenu3 = new MenuModel();
		subMenu3.setName("别瞎点");
		subMenu3.setType("click");
		subMenu3.setKey("CREZY_P_M001003");

		// MenuModel subMenu4 = new MenuModel();
		// subMenu4.setName("秀man转啊转");
		// subMenu4.setType("view");
		// subMenu4.setUrl("http://www.doubifan.com/weixin/su.do?oid="
		// + weixinName + "&goType=fweel");

		MenuModel subMenu5 = new MenuModel();
		subMenu5.setName("带着偶吧去旅行");
		subMenu5.setType("view");
		subMenu5.setUrl("http://www.doubifan.com/userActivity/authActivity.do?oid="
				+ weixinName + "&goType=activity_index&state=yanyalun");

		listSubMenu.add(subMenu5);
		// listSubMenu.add(subMenu4);
		listSubMenu.add(subMenu3);
		listSubMenu.add(subMenu2);
		listSubMenu.add(subMenu);
		mmParent.setSub_button(listSubMenu);

		MenuModel mmParent2 = new MenuModel();
		mmParent2.setName("Fan局");

		List<MenuModel> mmSubList2 = new ArrayList<MenuModel>();
		MenuModel mmSub2 = new MenuModel();
		mmSub2.setName("Fan嗑");
		mmSub2.setType("view");
		mmSub2.setUrl("http://www.doubifan.com/weixin/su.do?oid=" + weixinName
				+ "&goType=fansay");
		// mmSub2.setUrl("http://115.29.224.66/weixin/su.do?oid=" + weixinName
		// + "&goType=fansay");

		MenuModel mmSub3 = new MenuModel();
		mmSub3.setName("表白");
		mmSub3.setType("view");
		mmSub3.setUrl("http://www.doubifan.com/weixin/su.do?oid=" + weixinName
				+ "&goType=wish");
		// mmSub3.setUrl("http://115.29.224.66/weixin/su.do?oid=" + weixinName
		// + "&goType=wish");

		MenuModel mmSub4 = new MenuModel();
		mmSub4.setName("公会");
		mmSub4.setType("view");
		mmSub4.setUrl("http://www.doubifan.com/weixin/su.do?oid=" + weixinName
				+ "&goType=group");
		// mmSub4.setUrl("http://115.29.224.66/weixin/su.do?oid=" + weixinName
		// + "&goType=group");

		mmSubList2.add(mmSub3);
		mmSubList2.add(mmSub2);
		mmSubList2.add(mmSub4);

		mmParent2.setSub_button(mmSubList2);

		listParent.add(mmFans);
		listParent.add(mmParent);
		listParent.add(mmParent2);

		tm.setButton(listParent);

		String json = JSON.toJSONString(tm);
		return json;
	}
}
