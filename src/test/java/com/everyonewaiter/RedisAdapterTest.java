package com.everyonewaiter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@DataRedisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class RedisAdapterTest {

	@Autowired
	RedisConnectionFactory redisConnectionFactory;

	static RedisContainer redis = new RedisContainer().withExposedPorts(6379);

	static {
		redis.start();
	}

	@BeforeAll
	static void setUp() {
		System.setProperty("spring.data.redis.host", redis.getHost());
		System.setProperty("spring.data.redis.port", redis.getFirstMappedPort().toString());
	}

	@AfterEach
	void clean() {
		try (var connection = redisConnectionFactory.getConnection()) {
			connection.serverCommands().flushDb();
		}
	}

	static class RedisContainer extends GenericContainer<RedisContainer> {

		public RedisContainer() {
			super(DockerImageName.parse("redis:7.4.1-alpine"));
		}
	}
}
