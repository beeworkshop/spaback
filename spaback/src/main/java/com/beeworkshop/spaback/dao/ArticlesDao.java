package com.beeworkshop.spaback.dao;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author beeworkshop
 * @description 文章编辑的DAO层实现
 *
 *              提供文章编辑所需要的细粒度数据库操作支持
 */
public interface ArticlesDao {
	/**
	 * 新增文章
	 */
	int addArticle(JSONObject json);

	/**
	 * 统计文章总数
	 */
	int countArticles(JSONObject json);

	/**
	 * 文章列表
	 */
	List<JSONObject> listArticles(JSONObject json);

	/**
	 * 更新文章
	 */
	int updateArticle(JSONObject json);
}
