package cn.smbms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Role;

public interface RoleMapper {

	/**
	 * 获取角色列表
	 * @return
	 */
	List<Role> findByRoleAll();
	
	Integer saveRole(Role role);
	
	Integer updateRole(Role role);
	
	Role findById(@Param("id")Integer id);
}
