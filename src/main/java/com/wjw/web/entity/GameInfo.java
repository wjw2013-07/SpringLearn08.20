package com.wjw.web.entity;

import java.io.Serializable;

public class GameInfo implements Serializable{
	
	private String name;
	private int code;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
	
}
