package com.sirius.skymall.redis.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisMonitor {
	@Autowired
	private StringRedisTemplate redisTemplate;

	public String info() {
		Properties pro = redisTemplate.getConnectionFactory().getConnection()
				.info();
		StringBuffer sb = new StringBuffer();
		Set<Object> keySet=pro.keySet();
		for(Object key : keySet){
		    Object value = pro.get(key);
		    sb.append(key.toString());
		    sb.append("=");
		    sb.append(value.toString());
		    sb.append(";<br/>");
		}
		return sb.toString();
	}
	
	public String keyMap() {
		Set<String> keySet = redisTemplate.keys("*");
		Iterator<String> keyIterator = keySet.iterator();
		Map<String,Long> keyMap = new HashMap<String,Long>();
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			if(redisTemplate.type(key).equals(DataType.LIST)){
				keyMap.put(key, redisTemplate.opsForList().size(key));
			}
			
		}
		return keyMap.toString();
	}

	public String ping() {
		return redisTemplate.getConnectionFactory().getConnection().ping();
	}

}
