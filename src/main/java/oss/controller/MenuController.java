package oss.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oss.entity.Menu;
import oss.service.MenuService;
import oss.util.RSAUtils;
import oss.util.TreeBuilderMenu;


@Controller
public class MenuController {
	@Autowired private MenuService menuService;

	@RequestMapping(value = "/menux", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String menux(int i,HttpSession session) {
		List<Menu> menus=menuService.menuX(i);
		String json=new TreeBuilderMenu().buildTree(menus);
	/*	Map<String, String> map=new HashMap<>();
		map = RSAUtils.createRSAKeys();
		session.setAttribute("publickey", map.get("public"));
		session.setAttribute("privatekey", map.get("private"));*/
		return json;
	}  
	
    @RequestMapping(value = "/menuma", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String menuma(int i) {
		List<Menu> menus=menuService.getTreeMenu(i);
		String json=new TreeBuilderMenu().buildTree(menus);
		return json;
	}
    
    @RequestMapping(value = "/menuo",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Menu> menuo(int i ){
		List<Menu> menus=menuService.menuX(i);
		return menus;
	}
	
	 @RequestMapping(value = "/menuEdit", produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String menuEdit(Menu menu) {
		 int i=menuService.updateByPrimaryKeySelective(menu);
			return "1";
		}
	 
	 @RequestMapping(value = "/menuAdd", produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String menuAdd(String menuText,String menuUrl,String menuIcon,int menuType,int sort) {
		 Menu menu=new Menu();
		 menu.setIcon(menuIcon);
		 menu.setMenuText(menuText);
		 menu.setUrl(menuUrl);
		 menu.setMenuType(menuType);
		 menu.setSort(sort);
		 menu.setState(1);
		 menu.setPid(0);
		 menu.setIsLeaf(0);
		 int i=menuService.addMenu(menu);
			return "1";
		}
	 
	 @RequestMapping(value = "/menuAdd2", produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String menuAdd2(String menuText,String menuUrl,int menuType,int sort,int pid) {
		 Menu menu=new Menu();
		 Menu menu2=new Menu();
		 menu2.setSort(sort);
		 menu2.setMenuType(menuType);
		 menu.setMenuText(menuText);
		 menu.setUrl(menuUrl);
		 menu.setMenuType(menuType);
		 menu.setSort(sort);
		 menu.setState(1);
		 menu.setPid(pid);
		 menu.setIsLeaf(1);
		 int i2=menuService.orderplus(menu2);
		 int i=menuService.addMenu(menu);
			return "1";
		}
	 
	 @RequestMapping(value = "/menuDel", produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String menuDel(int id,int menuType) {
		int i=menuService.getSortById(id);
		Menu menu=new Menu();
		menu.setMenuType(menuType);
		menu.setSort(i);
		int ii=menuService.orderreduce(menu);
		int iii=menuService.deleteByPrimaryKey(id);
			return "1";
		}
	 
	 
	/*@RequestMapping(value = "/RSATest", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String RSATest(String Enname,String Enpass,HttpSession session) {
		System.out.println(Enname);
		System.out.println(Enpass);
		String dename=RSAUtils.decode(Enname, session.getAttribute("privatekey").toString());
		String depass=RSAUtils.decode(Enpass, session.getAttribute("privatekey").toString());
		System.out.println(dename);
		System.out.println(depass);
		return "1";
	}*/


}
