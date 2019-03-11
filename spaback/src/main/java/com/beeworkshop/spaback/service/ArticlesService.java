package com.beeworkshop.spaback.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author beeworkshop
 * @description 文章编辑service层
 */
public interface ArticlesService {
	/**
	 * 新增文章
	 */
	JSONObject addArticle(JSONObject json);

	/**
	 * 文章列表
	 */
	JSONObject listArticles(JSONObject json);

	/**
	 * 更新文章
	 */
	JSONObject updateArticle(JSONObject json);
}
