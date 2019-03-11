package com.beeworkshop.spaback.commons.exception;

import com.alibaba.fastjson.JSONObject;
import com.beeworkshop.spaback.utils.CommonTools;
import com.beeworkshop.spaback.utils.ErrorDef;

/**
 * 
 * @author beeworkshop
 * @description: 本系统使用的自定义错误类
 *               <p>
 *               比如在校验参数时,如果不符合要求,可以抛出此错误类 拦截器可以统一拦截此错误,将其中json返回给前端
 *
 */
public class CommonJsonException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JSONObject resultJson;

	/**
	 * 调用时可以在任何代码处直接throws这个Exception, 都会统一被拦截,并封装好json返回给前台
	 *
	 * @param ErrorDef 以错误的Error Enum做参数
	 */
	public CommonJsonException(ErrorDef errDef) {
		this.resultJson = CommonTools.errorJson(errDef);
	}

	public CommonJsonException(JSONObject resultJson) {
		this.resultJson = resultJson;
	}

	public JSONObject getResultJson() {
		return resultJson;
	}
}
