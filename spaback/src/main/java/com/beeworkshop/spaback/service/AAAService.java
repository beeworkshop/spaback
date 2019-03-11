package com.beeworkshop.spaback.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author beeworkshop
 * @description 登录用户的认证逻辑接口
 *              <p>
 *              我们通常会把 事务 配置在 Service层，当数据库操作失败时让 Service 层抛出运行时异常，Spring
 *              事物管理器就会进行回滚。
 */
public interface AAAService {
	/**
	 * 查询登录用户的账号授权信息。这里的方法名可以与DAO中定义的方法名同名，但是内涵不同。Service是去调用DAO提供的细粒度基础操作来实现自己的逻辑。
	 * 
	 * 概念上AAAService.getUserAccount()>AAADao.getUserAccount()
	 * 
	 * AAAService.getUserAccount(){
	 * 
	 * AAADao.getUserAccount() //为service层提供服务
	 * 
	 * 其他处理逻辑......
	 * 
	 * }
	 * 
	 * 
	 */
	JSONObject getUserAccount(String username);

}
