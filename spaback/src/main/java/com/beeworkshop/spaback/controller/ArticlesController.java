package com.beeworkshop.spaback.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.service.ArticlesService;
import com.beeworkshop.spaback.utils.CommonTools;

/**
 * 
 * @author beeworkshop
 * @description 文章业务controller层
 */
@RestController
@RequestMapping("/article")
public class ArticlesController {

	@Autowired
	private ArticlesService articlesService;

	/**
	 * 查询文章列表
	 */
	@RequiresPermissions("article:list")
	/*
	 * 上边的参数"article:list"对应数据库中的权限字段（详见UserRealm extends AuthorizingRealm）
	 * 
	 * SimpleAuthorizationInfo（doGetAuthorizationInfo()中）：
	 * 
	 * Simple POJO implementation of the {AuthorizationInfo} interface that stores
	 * roles and permissions as internal attributes.
	 * 
	 * @RequiresPermissions的参数是与SimpleAuthorizationInfo中记录的成员属性做比对。
	 */
	@GetMapping("/listArticle")
	public JSONObject listArticles(HttpServletRequest request) {
		return articlesService.listArticles(CommonTools.request2Json(request));
	}

	/**
	 * 新增文章
	 */
	@RequiresPermissions("article:add")
	@PostMapping("/addArticle")
	public JSONObject addArticle(@RequestBody JSONObject request) {
		// 对客户端的输入参数进行校验
		CommonTools.hasAllRequired(request, "content");

		return articlesService.addArticle(request);
	}

	/**
	 * 修改文章
	 */
	@RequiresPermissions("article:update")
	@PostMapping("/updateArticle")
	public JSONObject updateArticle(@RequestBody JSONObject request) {
		// 对客户端的输入参数进行校验
		CommonTools.hasAllRequired(request, "id,content");

		return articlesService.updateArticle(request);
	}
}
