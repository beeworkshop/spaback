package com.beeworkshop.spaback.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.dao.UsersDao;
import com.beeworkshop.spaback.service.UsersService;
import com.beeworkshop.spaback.utils.CommonTools;
import com.beeworkshop.spaback.utils.ErrorDef;
import com.beeworkshop.spaback.utils.HashMd5;

/**
 * 
 * @author beeworkshop
 * @description 用户，角色，权限的编辑处理service层的实现类
 *
 *              依托基本的DAO提供的数据库操作，开始加入具体逻辑
 */
@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersDao usersDao;

	/**
	 * 用户列表
	 */
	@Override
	public JSONObject listUsers(JSONObject json) {
		CommonTools.fillPageParam(json);
		int count = usersDao.usersCount(json);
		List<JSONObject> list = usersDao.listUsers(json);
		return CommonTools.successPage(json, list, count);
	}

	/**
	 * 查询所有的角色
	 * 
	 * 在添加/修改用户的时候要使用此方法
	 */
	@Override
	public JSONObject getAllRoles() {
		List<JSONObject> roles = usersDao.getAllRoles();
		return CommonTools.successPage(roles);
	}

	/**
	 * 添加用户
	 */
	@Override
	public JSONObject addUser(JSONObject json) {
		int exist = usersDao.verifyUsername(json);
		if (exist > 0) {
			return CommonTools.errorJson(ErrorDef.E10009);
		}

		// 将客户端传来的明文密码做MD5哈希变换
		String username = json.getString("username");
		String password = json.getString("password");
		password = (new HashMd5()).hashMd5Encrypt(password, username + "\'s");
		json.put("password", password);

		usersDao.addUser(json);
		return CommonTools.successJson();
	}

	/**
	 * 修改用户
	 */
	@Override
	public JSONObject updateUser(JSONObject json) {
		// 对密码进行MD5哈希变换
		String password = json.getString("password");
		if (!CommonTools.isNullOrEmpty(password)) {
			String username = json.getString("username");
			password = (new HashMd5()).hashMd5Encrypt(password, username + "\'s");
			json.put("password", password);
		}

		usersDao.updateUser(json);
		return CommonTools.successJson();
	}

	/**
	 * 角色列表
	 */
	@Override
	public JSONObject listRoles() {
		List<JSONObject> roles = usersDao.listRoles();
		return CommonTools.successPage(roles);
	}

	/**
	 * 查询所有权限,给角色分配权限时调用
	 */
	@Override
	public JSONObject listAllPerms() {
		List<JSONObject> perms = usersDao.listAllPerms();
		return CommonTools.successPage(perms);
	}

	/**
	 * 添加角色
	 * 
	 * 注意：这里开始有事务了
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject addRole(JSONObject json) {
		usersDao.addRole(json);
		usersDao.addRolePerms(json.getString("roleId"), (List<Integer>) json.get("permissions"));
		return CommonTools.successJson();
	}

	/**
	 * 修改角色
	 * 
	 * 注意：这里开始有事务了
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject updateRole(JSONObject json) {
		String roleId = json.getString("roleId");
		List<Integer> newPerms = (List<Integer>) json.get("permissions");
		JSONObject roleInfo = usersDao.getRoleAllInfo(json);
		Set<Integer> originPerms = (Set<Integer>) roleInfo.get("permissionIds");

		// 修改角色名称
		modifyRoleName(json, roleInfo);
		// 添加新权限
		saveNewPerms(roleId, newPerms, originPerms);
		// 移除旧的不再拥有的权限
		removeOldPerms(roleId, newPerms, originPerms);

		return CommonTools.successJson();
	}

	/**
	 * 修改角色名称
	 */
	private void modifyRoleName(JSONObject json, JSONObject roleInfo) {
		String roleName = json.getString("roleName");
		if (!roleName.equals(roleInfo.getString("roleName"))) {
			usersDao.updateRoleName(json);
		}
	}

	/**
	 * 为角色添加新权限
	 */
	private void saveNewPerms(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
		List<Integer> waitInsert = new ArrayList<>();
		for (Integer newPerm : newPerms) {
			if (!oldPerms.contains(newPerm)) {
				waitInsert.add(newPerm);
			}
		}
		if (waitInsert.size() > 0) {
			usersDao.addRolePerms(roleId, waitInsert);
		}
	}

	/**
	 * 删除角色旧的不再拥有的权限
	 */
	private void removeOldPerms(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
		List<Integer> waitRemove = new ArrayList<>();
		for (Integer oldPerm : oldPerms) {
			if (!newPerms.contains(oldPerm)) {
				waitRemove.add(oldPerm);
			}
		}
		if (waitRemove.size() > 0) {
			usersDao.delPastPerms(roleId, waitRemove);
		}
	}

	/**
	 * 删除角色
	 * 
	 * 注意：这里开始有事务了
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject deleteRole(JSONObject json) {
		JSONObject roleInfo = usersDao.getRoleAllInfo(json);
		List<JSONObject> users = (List<JSONObject>) roleInfo.get("users");
		if (users != null && users.size() > 0) {
			return CommonTools.errorJson(ErrorDef.E10008);
		}
		usersDao.delRole(json);
		usersDao.delRoleAllPerms(json);

		return CommonTools.successJson();
	}
}
