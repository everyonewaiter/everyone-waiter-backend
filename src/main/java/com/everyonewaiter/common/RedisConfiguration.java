package com.everyonewaiter.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
class RedisConfiguration {

	@Bean
	RedisTemplate<String, String> redisTemplate(LettuceConnectionFactory connectionFactory) {
		StringRedisTemplate redisTemplate = new StringRedisTemplate(connectionFactory);
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}
}
