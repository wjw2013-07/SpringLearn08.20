package com.wjw.web;

import org.junit.Test;

import com.wjw.web.cache.MemcacheManger;

public class TestWeb {
	
	@Test
	public void testListServers(){
		MemcacheManger.setCache("wangjunwei", 10000, "it is a test!");
		Object object = MemcacheManger.getCache("wangjunwei");
		System.out.println(object);
	}
}
