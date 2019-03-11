package com.beeworkshop.spaback.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.dao.AAADao;
import com.beeworkshop.spaback.service.AAAService;

/**
 * 
 * @author beeworkshop
 * @description AAAService实现类，提供认证的实现逻辑
 *
 */
@Service
public class AAAServiceImpl implements AAAService {

	// 在service层调用dao层/mapper层(DTO <-对应-> DB.table.row/list<DTO> <-对应->
	// DB.table.rows)提供的细粒度数据库操作
	// dao的具体实现详见相应的Mapper.xml
	// 在service中通过依赖注入(DI，即@Autowired)，来实现对dao层的调用
	@Autowired
	private AAADao aaaDao;

	/**
	 * 查询登录用户的授权及授权的资源(如对登录用户可用的菜单项)
	 * 
	 * 注意：dao的具体实现看相应的Mapper.xml
	 * 
	 * 在Mapper.xml中，结果集映射resultType/resultMap到JSONObject时，键值对的形式可以帮助去除重复数据
	 */
	@Override
	public JSONObject getUserAccount(String username) {
		JSONObject json = aaaDao.getUserAccount(username);

		// 管理员角色的用户拥有所有可能的权限并可显示所有可能的菜单项
		// 针对管理员角色的用户可以直接查询数据库获取所有可能的权限和菜单项
		// 因此单独判断处理如下：
		if (json.getIntValue("roleId") == 1) {
			Set<String> menuList = aaaDao.listAllMenus();
			Set<String> permissionList = aaaDao.listAllAuthors();
			// 填写json时注意key的名字不能错
			json.put("menuList", menuList);
			json.put("permissionList", permissionList);
		}

		return json;
	}

}
