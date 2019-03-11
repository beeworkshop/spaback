package com.beeworkshop.spaback.commons.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.utils.CommonTools;
import com.beeworkshop.spaback.utils.ErrorDef;

/**
 * 
 * @author beeworkshop
 * @description 拦截全局范围内的异常并处理
 *              <p>
 *              可以使用 @ControllerAdvice + @ExceptionHandler 进行全局的 Controller
 *              层异常处理。而且，只要设计得当，就再也不用在 Controller 层进行 try-catch 了！
 *              <p>
 * @Validated 校验器注解的异常，也可以一起处理，无需手动判断绑定校验结果 BindingResult/Errors 了
 *            <p>
 *            优点：将 Controller 层的异常和数据校验的异常进行统一处理，减少模板代码，减少编码量，提升扩展性和可维护性。
 *            缺点：只能处理 Controller 层未捕获（往外抛）的异常，对于 Interceptor（拦截器）层的异常，Spring
 *            框架层的异常，就无能为力了。
 */
//下边的注解定义全局异常处理类
@ControllerAdvice
//下边的注解使得返回JSON类型的数据
@ResponseBody
public class GlobalExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@ExceptionHandler(value = Exception.class)
	public JSONObject defaultErrorHandler(HttpServletRequest req, Exception e) {
		String errorPosition = "";
		// 如果错误堆栈信息存在
		if (e.getStackTrace().length > 0) {
			StackTraceElement element = e.getStackTrace()[0];
			String fileName = element.getFileName() == null ? "未找到错误文件" : element.getFileName();
			int lineNumber = element.getLineNumber();
			errorPosition = fileName + ":" + lineNumber;
		}
		JSONObject json = new JSONObject();
		json.put("code", ErrorDef.E400.getErrCode());
		json.put("msg", ErrorDef.E400.getErrMsg());
		JSONObject err = new JSONObject();
		err.put("errorLocation", e.toString() + "    错误位置:" + errorPosition);
		json.put("info", err);
		logger.error("异常", e);
		return json;
	}

	/**
	 * GET/POST请求错误的处理方法。因为开发时可能比较常见,而且发生在进入controller之前, 上面的错误处理方法拦截不到这个错误
	 * 所以定义了这个异常处理方法
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public JSONObject httpRequestMethodErrHandler() {
		return CommonTools.errorJson(ErrorDef.E500);
	}

	// 本项目自定义错误的处理方法 。
	// 拦截到此错误之后,就返回这个类里面的json给前端 。
	// 常见使用场景是参数校验失败,抛出此错,处理并返回错误信息给前端。
	@ExceptionHandler(CommonJsonException.class)
	public JSONObject commonJsonExceptionHandler(CommonJsonException commonJsonException) {
		return commonJsonException.getResultJson();
	}

	// 权限不足异常处理
	@ExceptionHandler(UnauthorizedException.class)
	public JSONObject unauthorizedExceptionHandler() {
		return CommonTools.errorJson(ErrorDef.E502);
	}

	// 处理未认证登录异常
	// 常见于未登录但希望使用需要权限的操作时出现
	@ExceptionHandler(UnauthenticatedException.class)
	public JSONObject unauthenticatedException() {
		return CommonTools.errorJson(ErrorDef.E20011);
	}

}
