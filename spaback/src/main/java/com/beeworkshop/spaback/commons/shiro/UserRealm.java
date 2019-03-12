package com.beeworkshop.spaback.commons.shiro;

import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.service.LoginService;
import com.beeworkshop.spaback.utils.Constants;

/**
 * 
 * @author beeworkshop
 * @description 自定义Realm
 *
 *              用于持久层的处理
 */
public class UserRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private LoginService loginService; // 需要依赖注入LoginService。她提供登录相关的涉及数据库操作的处理逻辑。

	@Override
	@SuppressWarnings("unchecked")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取用户的session
		Session session = SecurityUtils.getSubject().getSession();

		// 查询用户的权限
		JSONObject permission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
		logger.info("用户权限的JSON为:" + permission);
		logger.info("本用户权限为:" + permission.get("permissionList"));

		// 为当前用户设置角色和权限
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		// SimpleAuthorizationInfo的对象作为一个POJO，将记录从数据库中查询到的或从session中取出的赋予用户的权限
		// addStringPermissions()方法将权限字符串（如"user:list"）计入SimpleAuthorizationInfo对象
		// 后续在Controller中@RequiresPermissions("user:list")的参数将
		// 遍历比对SimpleAuthorizationInfo对象中记录的授权字符串（如"user:list"）
		authorizationInfo.addStringPermissions((Collection<String>) permission.get("permissionList"));
		return authorizationInfo;
	}

	/**
	 * 验证当前登录的Subject
	 * 
	 * LoginServiceImpl.loginAuthen()方法中执行Subject.login()时执行此方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取用户名
		String username = (String) token.getPrincipal();
		// 获取用户密码（已做MD5变换）
		String password = new String((char[]) token.getCredentials());
		// 访问数据库获取用户账号信息（保存在json中）
		JSONObject user = loginService.getUserAccount(username, password);

		if (user == null) {// 没找到帐号
			throw new UnknownAccountException();
		}

		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		//@formatter:off
		SimpleAuthenticationInfo authenticationInfo = 
				new SimpleAuthenticationInfo(
						user.getString("username"),
						user.getString("password"),
						//salt=username+salt，采用明文访问时，不需要此句
						// ByteSource.Util.bytes("salt"), 
						getName());
		//@formatter:on

		// session中不需要保存密码
		user.remove("password");

		// 将用户账号信息放入session中
		SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO, user);
		return authenticationInfo;
	}

}
