package cn.smbms.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.User;

public interface UserMapper {
	User findByName(@Param("name")String name);
	
	List<User> findByQuery(Map<String, Object> map);
	
	Integer countByQuery(Map<String, Object> map);
	
	Integer saveUser(User user);
	
	Integer updateUser(User user);
	
	Integer deleteUser(@Param("id")Integer id);
	
	User findById(@Param("id")Integer id);
}
