package com.beeworkshop.spaback.commons.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.utils.CommonTools;
import com.beeworkshop.spaback.utils.ErrorDef;

/**
 * 
 * @author beeworkshop
 * @description 系统错误拦截, 主要是针对404的错误
 *              <p>
 *              Spring Boot 将所有的错误默认映射到/error，如果希望自定义错误页面可以：
 *              <p>
 *              实现ErrorController。
 *              <p>
 *              但在前后端分离RESTful风格的编程中，后端不再负责路由，路由交给前端处理。 后端只返回错误信息的json即可。
 *              <p>
 *              Spring
 *              Boot提供了BasicErrorController类，AbstractErrorController抽象类和ErrorController接口来处理错误页面。
 *              <p>
 *              其中BasicErrorController继承了AbstractErrorController，而AbstractErrorController实现了ErrorController接口。
 * 
 *              这里通过实现ErrorController接口，提供一种修改默认页面Error Page及路由的方式。
 *
 *              实现方法中不提供路由及Error Page页面，只返回json。
 */
@Controller
public class WebErrController implements ErrorController {

	private static final String ERROR_PATH = "/error";

	/**
	 * 主要是处理登陆后各种错误路径，404页面改为返回json。
	 * 
	 * 未登录的情况下，大部分路由都已经被shiro拦截，并重定向，让用户登录。
	 *
	 * @return 501的错误信息json
	 */
	@RequestMapping(value = ERROR_PATH)
	@ResponseBody
	public JSONObject errorHandler() {
		return CommonTools.errorJson(ErrorDef.E501);
	}

	@Override
	public String getErrorPath() {
		// 直接返回表示错误的路由
		return ERROR_PATH;
	}

}
