package com.beeworkshop.spaback.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author beeworkshop
 * @description 用户，角色，权限的编辑处理持久层（DAO层）支持
 * 
 *              提供面向数据库的细粒度支持（增删改查）
 */
public interface UsersDao {
	/**
	 * 查询用户数量
	 */
	int usersCount(JSONObject json);

	/**
	 * 列出所有的用户
	 */
	List<JSONObject> listUsers(JSONObject json);

	/**
	 * 查询所有的角色 在添加/修改用户的时候要使用此方法
	 */
	List<JSONObject> getAllRoles();

	/**
	 * 校验用户名是否已存在
	 */
	int verifyUsername(JSONObject json);

	/**
	 * 新增用户
	 */
	int addUser(JSONObject json);

	/**
	 * 修改用户
	 */
	int updateUser(JSONObject json);

	/**
	 * 列出所有角色
	 */
	List<JSONObject> listRoles();

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	List<JSONObject> listAllPerms();

	/**
	 * 新增角色
	 */
	int addRole(JSONObject json);

	/**
	 * 批量为角色插入权限
	 *
	 * @param roleId      角色ID
	 * @param permissions 权限
	 * 
	 */
	// @Param()是mybatis的注解
	int addRolePerms(@Param("roleId") String roleId, @Param("permissions") List<Integer> perms);

	/**
	 * 一个角色拥有的权限其delete_status设置为1，表示权限拥有状态为：拥有
	 * 
	 * 当角色的权限被修改为不再拥有该权限时，其delete_status设置为2，表示权限拥有状态为：不再拥有
	 */
	int delPastPerms(@Param("roleId") String roleId, @Param("permissions") List<Integer> perms);

	/**
	 * 修改角色的名称
	 */
	int updateRoleName(JSONObject json);

	/**
	 * 查询某角色的全部数据 在删除和修改角色时调用
	 */
	JSONObject getRoleAllInfo(JSONObject json);

	/**
	 * 删除角色
	 */
	int delRole(JSONObject json);

	/**
	 * 删除本角色的全部权限
	 */
	int delRoleAllPerms(JSONObject json);

}
