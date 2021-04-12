package com.lhh.excep;

/**
 * @author lihonghao
 * @date 2021/4/12 16:24
 */
public class NotEnoughException extends RuntimeException{
	public NotEnoughException() {
		super();
	}

	public NotEnoughException(String message) {
		super(message);
	}
}
