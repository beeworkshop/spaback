package com.beeworkshop.spaback.model;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * @author beeworkshop
 * @description: MyBatis处理一对多场景并返回JSON对象
 *               <p>
 *               处理嵌套查询结果时(resultMap)，MyBatis会根据bean定义的属性类型来初始化嵌套的成员变量，
 *               主要看其是不是Collection， 如果这里不定义，那么嵌套返回结果里就只能返回一对一的结果，而不是一对多的结果
 *               <p>
 *               参见MyBatis
 *               DefaultResultSetHandler.instantiateCollectionPropertyIfAppropriate()
 *               该类是多个方法复用的
 */
@SuppressWarnings("unused")
public class UserAuthorInfo extends JSONObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	// 登录认证时使用
	private Set<String> menuList;
	private Set<String> permissionList; // UsersDao.listUsers()也用到。

	// UsersDao.listRoles()中用到
	private List<JSONObject> users;
	private List<JSONObject> menus;

	// UsersDao.permsMap()中用到
	private List<JSONObject> permissions; // 是permsMap中的

	// UsersDao.allRoleInfo()中用到
	private Set<Integer> permissionIds;

	// 没用到
//	private Set<String> roleList;
//	private List<JSONObject> picList;

}
