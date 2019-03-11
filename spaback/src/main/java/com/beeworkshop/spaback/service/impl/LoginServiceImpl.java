package com.beeworkshop.spaback.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.dao.LoginDao;
import com.beeworkshop.spaback.service.AAAService;
import com.beeworkshop.spaback.service.LoginService;
import com.beeworkshop.spaback.utils.CommonTools;
import com.beeworkshop.spaback.utils.Constants;

/**
 * 
 * @author beeworkshop
 * @description 登录service的实现类
 *              <p>
 *              实现用户的登录逻辑
 * @note 要面向抽象编程，所以先定义接口再实现之。下面就是具体的实现逻辑
 */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private AAAService aaaService;

	/**
	 * 用户登录认证处理
	 */
	@Override
	public JSONObject loginAuthen(JSONObject json) {
		// 取出用户名和密码
		String username = json.getString("username");
		String password = json.getString("password");

		// 构建登录返回信息的初始json
		JSONObject respInfo = new JSONObject();

		// 下边开始使用shiro进行登录认证工作
		Subject loginUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			loginUser.login(token); // 转去执行AuthorizingRealm.doGetAuthenticationInfo(token)
			respInfo.put("result", "success");
		} catch (AuthenticationException e) {
			respInfo.put("result", "fail");
		}

		return CommonTools.successJson(respInfo);
	}

	/**
	 * 根据用户名和密码查询对应的用户
	 */
	@Override
	public JSONObject getUserAccount(String username, String password) {
		return loginDao.getLoginUser(username, password);
	}

	/**
	 * 查询当前登录用户的权限等信息
	 * 
	 * 注意，这个时候用户已经登录了，有session了
	 */
	@Override
	public JSONObject getUserAuthor() {
		// 从session获取用户信息
		Session session = SecurityUtils.getSubject().getSession();

		JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
		String username = userInfo.getString("username");

		JSONObject userAuthor = aaaService.getUserAccount(username);
		session.setAttribute(Constants.SESSION_USER_PERMISSION, userAuthor);

		JSONObject info = new JSONObject();
		info.put("userPermission", userAuthor);

		return CommonTools.successJson(info);
	}

	/**
	 * 用户登出
	 */
	@Override
	public JSONObject logout() {
		try {
			Subject loginUser = SecurityUtils.getSubject();
			loginUser.logout();
		} catch (Exception e) {
			System.out.println("用户登出异常");
		}
		return CommonTools.successJson();
	}

}
