package com.journal.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisTests {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;// default serializer and deserializer if not set

	@Test
	void testRedisCcahe() {
		this.redisTemplate.opsForValue().set("email", "email@email.com");
		Assertions.assertNotNull(this.redisTemplate.opsForValue().get("email"));
	}
}