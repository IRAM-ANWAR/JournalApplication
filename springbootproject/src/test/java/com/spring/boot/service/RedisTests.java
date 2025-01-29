package com.spring.boot.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {

	@Autowired
	private RedisTemplate redisTemplate;// default serializer and deserializer if not set

	@Disabled
	@Test
	void testSendMail() {
		this.redisTemplate.opsForValue().set("email", "email@email.com");
		Object email = this.redisTemplate.opsForValue().get("email");
		int a = 1;
	}
}