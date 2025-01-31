package com.journal.redis;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisService {

	private RedisTemplate<String, String> redisTemplate;

	ObjectMapper mapper = new ObjectMapper();

	public RedisService(RedisTemplate<String, String> redisTemplate) {
		super();
		this.redisTemplate = redisTemplate;
	}

	public <T> T get(String key, Class<T> entityClass) {
		try {
			Object o = this.redisTemplate.opsForValue().get(key);
			return this.mapper.readValue(o.toString(), entityClass);
		} catch (Exception e) {
			log.error("Exception ", e);
			return null;
		}
	}

	public void set(String key, Object o, Long timeToLive) {
		try {
			String jsonValue = this.mapper.writeValueAsString(o);
			this.redisTemplate.opsForValue().set(key, jsonValue, timeToLive, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("Exception ", e);
		}
	}

}