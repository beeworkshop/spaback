package com.beeworkshop.spaback.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author beeworkshop
 * @description 登录部分涉及持久层的逻辑处理
 * @note service涉及持久层的处理，但都是粗粒度的数据库操作。其中包含业务逻辑，而且主要是业务逻辑。service层将调用dao层提供的基础服务。接口定义不使用@Service注解
 */
public interface LoginService {
	/**
	 * 登录认证处理
	 */
	JSONObject loginAuthen(JSONObject json);

	/**
	 * 查询登录用户账户信息
	 */
	JSONObject getUserAccount(String username, String password);

	/**
	 * 获取登录用户权限。 用户为当前登入的用户
	 */
	JSONObject getUserAuthor();

	/**
	 * 注销处理
	 */
	JSONObject logout();
}
