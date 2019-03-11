package com.beeworkshop.spaback.dao;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;

/**
 * @author beeworkshop
 * @description 登录验证的数据库访问DAO定义
 *
 */
public interface LoginDao {
	/**
	 * 只从数据库表中取2个字段，不是把所有字段映射到DTO/POJO，故不用@Mapper注解
	 * 这里不使用自定义DTO/POJO，而是使用阿里巴巴的fastjson——JSONObject，映射数据库表中感兴趣的字段
	 * Dao在这里定义的方法是访问数据库的最细粒度的操作，这里涉及登录用户的账户信息查询
	 */
	JSONObject getLoginUser(@Param("username") String username, @Param("password") String password);
}
