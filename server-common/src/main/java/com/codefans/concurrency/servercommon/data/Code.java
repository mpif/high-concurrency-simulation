package com.codefans.concurrency.servercommon.data;

/**
 * 返回码枚举。
 * 
 * 说明
 * 系统类异常 1000-1999段
 * 系统异常1000
 * 网络异常1100
 * 公共类异常2000-2999段 (例如解析失败，参数为空)
 *
 */
public enum Code {

	/**
	 * 成功。
	 */
	SUCCESS(200),

	/**
	 * 失败。
	 */
	FAIL(300),

	/**
	 * 系统异常
	 */

	SYSTEMEXCEPTION(1000),

	/**
	 * 调用服务失败
	 */

	CALLSERVICEEXCEPTION(1101),

	/**
	 * 参数为空。
	 */
	PARAMATERSISNULL(2000),

	/**
	 * 查询结果集为空
	 */
	RESULTSETISNULL(2001),
	/**
	 * 
	 * 参数解析异常
	 */

	PARAMPARSEEXCEPTION(2002),

	/**
	 * 
	 * 类名未找到
	 */

	CLASSNOTFOUNDEXCEPTION(2003);


	private int value;

	private Code(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
