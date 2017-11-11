package cn.smbms.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.User;
import cn.smbms.tools.Page;

public interface UserService {
User findByName(String name);
	
	List<User> findByQuery(Map<String, Object> map);
	
	Integer countByQuery(Map<String, Object> map);
	
	Integer saveUser(User user);
	
	Integer updateUser(User user);
	
	Integer deleteUser(Integer id);
	
	User findById(Integer id);
	
	Page<User> findByPage(Map<String, Object> map);
}
