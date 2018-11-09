package oss.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import oss.entity.User_Table;
import oss.service.LoginService;
import oss.util.MD5Hex;

@Controller
public class LoginController {
	
	@Autowired private LoginService loginService;
	
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
	
	@RequestMapping("/registration")
	public String registration() {
		return "registration";
	}
	
	@RequestMapping("/jump/jumplogin")
	public String jumplogin() {
		return "jump/jumplogin";
	}
	
	@RequestMapping("/menumanage")
	public String menumanage() {
		return "menumanage";
	}
	
	@RequestMapping(value = "/ajax_login", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String login_submit(String name, String pass, HttpServletRequest request, HttpSession session) {
		boolean result = loginService.execute(name, pass);
		if (result) {
			User_Table user = loginService.getUserByName(name);
			int usertype = user.getUserType();
			int user_id=user.getUserId();
			String user_number = user.getUserName();
			String account_name=user.getRealName();
			Integer i=loginService.getRole_Id(user.getUserId());
			if (usertype == 0) {
				User_Table user2 = loginService.getUser(name, pass);
				session.setAttribute("user", user2);
				session.setAttribute("account_name", account_name);
				session.setAttribute("user_type", usertype);
				session.setAttribute("role_id", i);
				session.setAttribute("user_number", user_number);
				session.setAttribute("user_id", user_id);
				return "管理员登录成功";
			}
			else{
				User_Table user2 = loginService.getUser(name, pass);
				session.setAttribute("user", user2);
				session.setAttribute("account_name", account_name);
				session.setAttribute("user_type", usertype);
				session.setAttribute("role_id", i);
				session.setAttribute("user_number", user_number);
				session.setAttribute("user_id", user_id);
				return "学员登录成功";
			}
		} else {
			return "用户名或密码错误";
		}
		
	}
	@RequestMapping(value = "/ajax_registration", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String registration_submit(User_Table user_Table) {
		int i=loginService.registration(user_Table);
		User_Table user = loginService.getUserByName(user_Table.getUserName());
		int ii = loginService.updataUserRole(user.getUserId());
		if(i!=0) {
			return "0";
		}else {
			return "1";
		}
	}
	
	
}
