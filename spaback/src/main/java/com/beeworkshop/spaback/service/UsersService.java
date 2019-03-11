package com.beeworkshop.spaback.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author beeworkshop
 * @description 用户，角色，权限的编辑处理service层
 * 
 *              结合数据库操作，准备加入逻辑
 */
public interface UsersService {
	/**
	 * 用户列表
	 */
	JSONObject listUsers(JSONObject json);

	/**
	 * 查询所有的角色。在添加/修改用户信息的时候要使用此方法
	 */
	JSONObject getAllRoles();

	/**
	 * 添加用户
	 */
	JSONObject addUser(JSONObject json);

	/**
	 * 修改用户
	 */
	JSONObject updateUser(JSONObject json);

	/**
	 * 角色列表
	 */
	JSONObject listRoles();

	/**
	 * 查询所有权限。给角色分配权限时调用
	 */
	JSONObject listAllPerms();

	/**
	 * 添加角色
	 */
	JSONObject addRole(JSONObject json);

	/**
	 * 修改角色
	 */
	JSONObject updateRole(JSONObject json);

	/**
	 * 删除角色
	 */
	JSONObject deleteRole(JSONObject json);
}
