package cn.smbms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smbms.mapper.UserMapper;
import cn.smbms.pojo.User;
import cn.smbms.service.UserService;
import cn.smbms.tools.Page;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Autowired
	UserMapper userMapper;
	
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public User findByName(String name) {
		// TODO Auto-generated method stub
		return userMapper.findByName(name);
	}

	public List<User> findByQuery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.findByQuery(map);
	}

	public Integer countByQuery(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return userMapper.countByQuery(map);
	}

	public Integer saveUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.saveUser(user);
	}

	public Integer updateUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUser(user);
	}

	public Integer deleteUser(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.deleteUser(id);
	}

	public User findById(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.findById(id);
	}

	public Page<User> findByPage(Map<String, Object> map) {
		Integer count = userMapper.countByQuery(map);
		Page<User> pa = new Page<User>();
		pa.setCount(count);
		pa.setIndex(Integer.parseInt(map.get("index").toString()));
		map.put("index", (pa.getIndex()-1)*pa.getSize());
		map.put("size", pa.getSize());
		List<User> list = userMapper.findByQuery(map);
		pa.setList(list);
		return pa;
	}

}
