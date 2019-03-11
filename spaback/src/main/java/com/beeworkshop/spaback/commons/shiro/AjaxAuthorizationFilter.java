package com.beeworkshop.spaback.commons.shiro;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.utils.ErrorDef;

/**
 * 
 * @author beeworkshop
 * @description 对没有登录的请求进行拦截过滤，全部返回json信息.。覆盖掉shiro原来跳转login.jsp的拦截处理方式。
 */
public class AjaxAuthorizationFilter extends FormAuthenticationFilter {
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
		JSONObject json = new JSONObject();
		json.put("code", ErrorDef.E20011.getErrCode());
		json.put("msg", ErrorDef.E20011.getErrMsg());

		PrintWriter out = null;
		HttpServletResponse res = (HttpServletResponse) response;

		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json");
			out = response.getWriter();
			out.println(json);
		} catch (Exception e) {
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}

		return false;
	}

	@Bean
	public FilterRegistrationBean<?> registration(AjaxAuthorizationFilter filter) {
		FilterRegistrationBean<?> registration = new FilterRegistrationBean<>(filter);
		registration.setEnabled(false); // 留待手动控制
		return registration;
	}
}
