package com.beeworkshop.spaback.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.service.UsersService;
import com.beeworkshop.spaback.utils.CommonTools;

/**
 * 
 * @author beeworkshop
 * @description 用户，角色，权限的编辑处理controller层
 */
@RestController
@RequestMapping("/user")
public class UsersController {
	/*
	 * 用户登录->服务端为登录用户生成session（tomcat生成的sessionId叫做jsessionid）。
	 * 
	 * ==========================================================================
	 * jsessionid生成原理：ManagerBase类提供创建sessionid的方法：随机数+时间+jvmid。
	 * 
	 * session在Java中是通过调用HttpServletRequest的getSession方法（使用true作为参数）创建的
	 * 
	 * session在Tomcat上有生命期计时器，超时时间一般为20分钟。
	 * 
	 * session销毁只能通过invalidate或超时，客户端关掉浏览器并不会关闭session。
	 * 
	 * sesssion存储在服务器内存中，sessionId保存在客户端cookie中。session也可以持久化到文件，数据库，memcache，
	 * redis等中。
	 * 
	 * cookie承载于http的报头中。
	 * 
	 * 发到客户端的只有SessionID，当客户端再次发送请求的时候，会将这个SessionID带上，
	 * 服务器接收到请求之后就会依据这个SessionID找到相应的Session。
	 * 
	 * cookie的默认过期时间是会话级别。当用户关闭了浏览器，那么存储在客户端的session
	 * ID便会丢失，但是存储在服务器端的session数据并不会被立即删除。
	 * 
	 * 当浏览器禁用Cookie后，解决方案是：URL重写。即在URL中带上jsessionid参数。 重写方法：
	 * 
	 * response.encodeRedirectURL(java.lang.String url) 和
	 * response.encodeURL(java.lang.String url)
	 * 
	 * 把cookie转换成response的参数
	 * 
	 * 把session id直接附加在URL路径的后面，附加方式也有两种
	 * 
	 * 一种是作为URL路径的附加信息，表现形式为http://...../xxx;jsessionid=
	 * ByOK3vjFD75aPnrF7C2HmdnV6QZcEbzWoWiBYEnLerjQ99zWpBng!-1457887
	 * 
	 * 另一种是作为查询字符串附加在URL后面，表现形式为http://...../xxx?jsessionid=
	 * ByOK3vjFD75aPnrF7C2HmdnV6QZcEbzWoWiBYEnLerjQ99zWpBng!-145788764
	 * 
	 * 为了在整个交互过程中始终保持状态，就必须在每个客户端可能请求的路径后面都包含这个session id。
	 * 
	 * 另一种技术叫做表单隐藏字段。就是服务器会自动修改表单，添加一个隐藏字段，以便在表单提交时能 够把session id传递回服务器。这种技术不常见。
	 * 
	 * ======================================================================
	 * 
	 * 服务器端根据登录的用户名查询数据库把权限信息写入session。
	 * 
	 * 用户再次访问时，根据cookie中的sessionID索引到相应的session，从中再读出权限信息，决定展现内容和控制。
	 * 
	 * 在AuthorizingRealm的doGetAuthenticationInfo()中把登录用户的账号信息设置进她的session
	 * 
	 * 在LoginServiceImpl.getUserAuthor()中通过shiro的SecurityUtils.getSubject().
	 * getSession()获得session，之后把权限设置给session。
	 * 
	 * 在用户登录认证后，LoginController的@PostMapping("/getInfo")处理中调用getUserAuthor()
	 * 将当前登录用户的权限放入session。权限信息通过session中记录的用户账号在通过DAO查询数据库可得到。
	 * 
	 * 过程如下：
	 * 
	 * 用户登录->认证通过->为用户建立session->用户账号写入session->根据用户账号DAO查询数据库->得到授权信息->
	 * 把授权信息写入session->用户再次访问服务http头中的cookie中的jsessionid索引session。
	 */

	/*
	 * shiro五个注解的处理顺序：
	 * 
	 * RequiresRoles -> RequiresPermissions -> RequiresAuthentication ->
	 * RequiresUser -> RequiresGuest
	 */

	@Autowired
	private UsersService usersService;

	/**
	 * 查询用户列表
	 * 
	 * 在shiro的realm中实现的授权
	 */
	@RequiresPermissions("user:list")
	/*
	 * 上边的参数"user:list"对应数据库中的权限字段（详见UserRealm extends AuthorizingRealm）
	 * 
	 * SimpleAuthorizationInfo（doGetAuthorizationInfo()中）：
	 * 
	 * Simple POJO implementation of the {AuthorizationInfo} interface that stores
	 * roles and permissions as internal attributes.
	 * 
	 * @RequiresPermissions的参数是与SimpleAuthorizationInfo中记录的成员属性做比对。
	 */
	@GetMapping("/list")
	public JSONObject listUsers(HttpServletRequest request) {
		return usersService.listUsers(CommonTools.request2Json(request));
	}

	/**
	 * 添加用户
	 */
	@RequiresPermissions("user:add")
	@PostMapping("/addUser")
	// @RequestBody将http request转化成json
	public JSONObject addUser(@RequestBody JSONObject request) {
		// 对客户端发来的参数进行校验
		CommonTools.hasAllRequired(request, "username,password,nickname,roleId");

		return usersService.addUser(request);
	}

	/**
	 * 修改用户账号信息
	 */
	@RequiresPermissions("user:update")
	@PostMapping("/updateUser")
	public JSONObject updateUser(@RequestBody JSONObject request) {
		// 对客户端发来的参数进行校验
		CommonTools.hasAllRequired(request, "nickname,roleId,deleteStatus,userId");

		return usersService.updateUser(request);
	}

	/**
	 * 取得所有角色信息
	 */
	@RequiresPermissions(value = { "user:add", "user:update" }, logical = Logical.OR)
	@GetMapping("/getAllRoles")
	public JSONObject getAllRoles() {
		return usersService.getAllRoles();
	}

	/**
	 * 角色列表
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listRole")
	public JSONObject listRoles() {
		return usersService.listRoles();
	}

	/**
	 * 查询所有权限, 给角色分配权限时调用
	 */
	@RequiresPermissions("role:list")
	@GetMapping("/listAllPermission")
	public JSONObject listAllPerms() {
		return usersService.listAllPerms();
	}

	/**
	 * 新增角色
	 */
	@RequiresPermissions("role:add")
	@PostMapping("/addRole")
	public JSONObject addRole(@RequestBody JSONObject request) {
		// 对客户端发来的参数进行校验
		CommonTools.hasAllRequired(request, "roleName,permissions");

		return usersService.addRole(request);
	}

	/**
	 * 修改角色
	 */
	@RequiresPermissions("role:update")
	@PostMapping("/updateRole")
	public JSONObject updateRole(@RequestBody JSONObject request) {
		// 对客户端发来的参数进行校验
		CommonTools.hasAllRequired(request, "roleId,roleName,permissions");

		return usersService.updateRole(request);
	}

	/**
	 * 删除角色
	 */
	@RequiresPermissions("role:delete")
	@PostMapping("/deleteRole")
	public JSONObject deleteRole(@RequestBody JSONObject request) {
		// 对客户端发来的参数进行校验
		CommonTools.hasAllRequired(request, "roleId");

		return usersService.deleteRole(request);
	}

}
