package com.wjw.web.cache;


import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

public class MemcacheManger {

	private  static MemcachedClient client = null;
	
	public  synchronized static MemcachedClient getMemcachedClient(){
		if (client == null) {
			try {
				
				MemcachedClientBuilder builder = new XMemcachedClientBuilder(
						AddrUtil.getAddresses("192.168.6.195:10001"));
				try {
					client = builder.build();
				} catch (Exception e) {
				}
				//client = new XMemcachedClient("192.168.6.59", 8080);
				//client.setEnableHeartBeat(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	
	public static void setCache(String key, int time, Object obj){
		client = getMemcachedClient();
		System.out.println("at setCache client =" + client);
		try {
			client.set(key, time, obj);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
	}
	
	public static Object getCache(String key){
		client = getMemcachedClient();
		System.out.println("at getCache client =" + client);
		Object obj = null;
		try {
			obj = client.get(key);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	
}
