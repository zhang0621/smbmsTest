package cn.smbms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.RoleService;
import cn.smbms.service.UserService;
import cn.smbms.tools.Page;

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
	
	@RequestMapping(value = "userview.html", method = RequestMethod.GET)
	public String userview(Model model
			,@RequestParam(value = "uid", required = false) String uid) {
		User user = userService.findById(Integer.parseInt(uid));
		model.addAttribute("user", user);
		return "userview";
	}
	
	@RequestMapping(value = "user.html", method = RequestMethod.GET)
	public String userShou(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", 1);
		map.put("size", 5);
		Page<User> page = userService.findByPage(map);
		List<Role> list = roleService.findByRoleAll();
		model.addAttribute("roleList", list);
		model.addAttribute("page", page);
		return "userlist";
	}
	/**
	 * 分页
	 * @param model
	 * @param pageIndex
	 * @param queryname
	 * @param queryUserRole
	 * @param method
	 * @return
	 */
	@RequestMapping(value = "user.html", method = RequestMethod.POST)
	public String userShow(Model model,
			@RequestParam(value = "pageIndex", required = false) String pageIndex,
			@RequestParam(value = "queryname", required = false) String queryname,
			@RequestParam(value = "queryUserRole", required = false) String queryUserRole,
			@RequestParam(value = "method", required = false) String method) {
		List<Role> list = roleService.findByRoleAll();
		model.addAttribute("roleList", list);
		// 这里应该有一个首页面的展示 Page分页对象 显示首页信息
		// 用来实现分页展示
		if ("".equals(pageIndex) || null == pageIndex) {
			pageIndex = "1";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (queryname != null && !"".equals(queryname)) {
			map.put("userName", queryname);
		}
		if (queryUserRole != null && !"".equals(queryUserRole) && !"0".equals(queryUserRole)) {
			map.put("roleId", queryUserRole);
		}
		map.put("index", pageIndex);
		map.put("size", 5);
		Page<User> page = userService.findByPage(map);
		model.addAttribute("queryname", map.get("userName"));
		model.addAttribute("queryUserRole", map.get("roleId"));
		model.addAttribute("page", page);
		return "userlist";
	}
	
	@RequestMapping(value = "usermodify.html", method = RequestMethod.GET)
	public String usermodify(Model model
			,@RequestParam(value = "uid", required = false) String uid) {
		User user = userService.findById(Integer.parseInt(uid));
		model.addAttribute("user", user);
		return "usermodify";
	}
	
	@RequestMapping("role.json")
	@ResponseBody
	public Object roleList() {
		List<Role> list = roleService.findByRoleAll();
		return JSON.toJSONString(list);
	}
	@RequestMapping(value = "userUpdate.html", method = RequestMethod.POST)
	public String userUpdate(Model model, User user, HttpSession session) {
		if (session.getAttribute("USER_CODE") != null) {
			User u = (User) session.getAttribute("USER_CODE");
			user.setModifyBy(u.getId());
		} else {
			user.setModifyBy(1);
		}
		user.setModifyDate(new Date());
		Integer id = userService.updateUser(user);
		if (id > 0) {
			return "redirect:user.html";
		} else {
			model.addAttribute("message", "抱歉因为网络原因更新失败！");
			return "usermodify";
		}
	}
	@RequestMapping(value = "userAdd.html", method = RequestMethod.GET)
	public String userAdd() {
		// userAdd.html
		return "useradd";
	}
	/**
	 * 新增
	 * @param model
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "userAdd.html", method = RequestMethod.POST)
	public String userAdd(Model model, User user, HttpSession session) {
		if (session.getAttribute("USER_CODE") != null) {
			User u = (User) session.getAttribute("USER_CODE");
			user.setCreatedBy(u.getId());
		} else {
			user.setCreatedBy(1);
		}
		user.setCreationDate(new Date());
		Integer id = userService.saveUser(user);
		if (id > 0) {
			return "redirect:user.html";
		} else {
			model.addAttribute("message", "抱歉因为网络原因添加失败！");
			return "useradd";
		}
	}
	/**
	 * getUserCode TODO(判断该用户编号是否存在)
	 * @param userCode
	 * @return
	 */
	@RequestMapping("getUserCode.json")
	@ResponseBody
	public Object getUserCode(@RequestParam("userCode") String userCode) {
		User user = userService.findByName(userCode);
		JSONObject json = new JSONObject();
		if (user != null) {
			json.put("userCode", "exist");
		} else {
			json.put("userCode", "success");
		}
		return json;
	}
	/** 
	 * 删除
	 * @param model
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "userdel.json",method=RequestMethod.POST)
	@ResponseBody
	public Object userdel(Model model
			,@RequestParam(value = "uid", required = false) String uid) {
		Integer result = userService.deleteUser(Integer.parseInt(uid));
		JSONObject json = new JSONObject();
		if(result == 1){
			json.put("delResult", "true");
		}else{
			json.put("delResult", "false");
		}
		return json;
	}	
	/**
	 * 跳转到密码修改界面
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="pwdmodify.html",method=RequestMethod.GET)
	public String updateUserOrPwd(HttpSession session,Model model){
		User user=(User) session.getAttribute("USER_CODE");
		model.addAttribute("user", user);
		return "pwdmodify";
	}
	/**
	 * 获取json
	 * @param session
	 * @param oldpassword
	 * @return
	 */
	@RequestMapping(value="pwdmodify.json",method=RequestMethod.GET)
	@ResponseBody
	public Object userJSON(HttpSession session,@RequestParam("oldpassword") String oldpassword){
		JSONObject json=new JSONObject();
		User user=(User) session.getAttribute("USER_CODE");
		if(oldpassword.equals(user.getUserPassword())){
			json.put("result", "true");
		}
		return json;
	}
	@RequestMapping("pwdmodifys.html")
	public String addPwd(HttpSession session,@RequestParam("newpassword") String newPassword){
		User user=(User) session.getAttribute("USER_CODE");
		User u=new User();
		u.setId(user.getId());
		u.setUserPassword(newPassword);
		Integer count=userService.updateUser(u);
		if(count>0){
			return "frame";
		}
		return "pwdmodify";
	}
}
