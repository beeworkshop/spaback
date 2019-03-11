package com.beeworkshop.spaback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.service.LoginService;
import com.beeworkshop.spaback.utils.CommonTools;

/**
 * 
 * @author beeworkshop
 * @description 登录相关Controller
 */
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	/**
	 * 用户登录
	 */
	@PostMapping("/auth")
	public JSONObject loginAuthen(@RequestBody JSONObject json) {
		// 前台输入数据验证
		CommonTools.hasAllRequired(json, "username,password");

		return loginService.loginAuthen(json);
	}

	/**
	 * 查询当前登录用户的授权信息
	 */
	@PostMapping("/getInfo")
	public JSONObject getInfo() {
		return loginService.getUserAuthor();
	}

	/**
	 * 用户登出
	 */
	@PostMapping("/logout")
	public JSONObject logout() {
		return loginService.logout();
	}
}
