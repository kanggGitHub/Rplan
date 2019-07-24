package com.cetc.plan.utils;

import com.alibaba.fastjson.JSONObject;
import com.cetc.plan.config.ResultCode;

import java.util.Map;

/**
 * 返回数据工具类
 *
 * @author kg
 * @date 2019年06月20日
 */
public class R extends JSONObject {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", ResultCode.SUCCESS.getValue());
		put("msg", ResultCode.SUCCESS);
	}

	public static R error() {
		return error(ResultCode.FAILURE.getValue(), "数据库服务器异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(ResultCode.FAILURE.getValue(), msg);
	}

	public static R databaseerror(String msg) {
		return error(ResultCode.DATABASES_OPERATION_FAIL.getValue(), msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(Long total) {
		R r = new R();
		r.put("total", total);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}

}
