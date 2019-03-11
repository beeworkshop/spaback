package com.beeworkshop.spaback.utils;

/**
 * 
 * @author beeworkshop
 * @description 定义web交互的错误码
 *
 */
public enum ErrorDef {
	//错误信息
	E400("400", "请求处理异常，请稍后再试"),
	E500("500", "请求方式有误,请使用GET/POST"),
	E501("501", "请求路径不存在"),
	E502("502", "权限不足"),
	E10008("10008", "角色删除失败,尚有用户属于此角色"),
	E10009("10009", "账户已存在"),
	E20011("20011", "登陆已过期,请重新登陆"),
	E90003("90003", "缺少必填参数");

	private String errCode;
	private String errMsg;

	ErrorDef(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

}
