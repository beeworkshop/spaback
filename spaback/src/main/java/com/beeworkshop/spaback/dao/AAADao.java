package com.beeworkshop.spaback.dao;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author beeworkshop
 * @description 登录用户的认证授权，DAO层的，面向数据库的细粒度操作
 * @note DAO的主要操作意图及逻辑看相应的Mapper.xml
 */
public interface AAADao {
	/**
	 * 登录 用户账户授权信息查询
	 */
	JSONObject getUserAccount(String username);

	/**
	 * 获取所有可能的权限
	 * 
	 * 返回的是一个集合：多条数据
	 * 
	 * 返回值类型在相应的Mapper.xml中，resultType="String"已经说明了
	 */
	Set<String> listAllAuthors();

	/**
	 * 获取所有可能的菜单项
	 * 
	 * 返回的是一个集合：多条数据
	 * 
	 * 返回值类型在相应的Mapper.xml中，resultType="String"已经说明了
	 */
	Set<String> listAllMenus();
}
