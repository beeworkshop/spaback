package com.beeworkshop.spaback.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.dao.ArticlesDao;
import com.beeworkshop.spaback.service.ArticlesService;
import com.beeworkshop.spaback.utils.CommonTools;

/**
 * 
 * @author beeworkshop
 * @description 文章编辑service层的具体实现类
 *
 *              借助DAO的依赖注入，完成基本数据库操作。此外，再加入业务逻辑。且主要是业务逻辑
 */
//注：service实现类必须使用下边的注解
@Service
public class ArticlesServiceImpl implements ArticlesService {

	@Autowired
	private ArticlesDao articlesDao;

	/**
	 * 新增文章（含事务处理）
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JSONObject addArticle(JSONObject json) {
		articlesDao.addArticle(json);
		return CommonTools.successJson();
	}

	/**
	 * 文章列表
	 */
	@Override
	public JSONObject listArticles(JSONObject json) {
		CommonTools.fillPageParam(json);
		int count = articlesDao.countArticles(json);
		List<JSONObject> list = articlesDao.listArticles(json);

		return CommonTools.successPage(json, list, count);
	}

	/**
	 * 更新文章（含事务处理）
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public JSONObject updateArticle(JSONObject json) {
		articlesDao.updateArticle(json);
		return CommonTools.successJson();
	}

}
