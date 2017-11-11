package cn.smbms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.smbms.pojo.User;
import cn.smbms.service.RoleService;
import cn.smbms.service.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired
	private RoleService roleService;
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService; 
	}
	
	@RequestMapping(value = "login.html", method = RequestMethod.GET)
	public String login() {
		return "../login";
	}
	
	@RequestMapping(value = "login.html", method = RequestMethod.POST)
	public String islogin(Model model,
			@RequestParam("userCode") String userCode,
			@RequestParam("userPassword") String userPassword,
			HttpSession session) {
		User user = userService.findByName(userCode);
		if (user != null) {
			if (user.getUserPassword().equals(userPassword)) {
				if (session.getAttribute("USER_CODE") != null) {
					session.removeAttribute("USER_CODE");
				}
				session.setAttribute("USER_CODE", user);
				session.setMaxInactiveInterval(60000);
				return "frame";
			} else {
				model.addAttribute("error", "抱歉密码不正确");
				return "../login";
			}
		} else {
			model.addAttribute("error", "抱歉账户名不正确");
			return "../login";
		} 
	}
}
