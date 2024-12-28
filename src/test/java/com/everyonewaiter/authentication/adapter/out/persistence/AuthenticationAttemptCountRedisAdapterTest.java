package com.everyonewaiter.authentication.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import com.everyonewaiter.RedisAdapterTest;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCount;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCountKey;
import com.everyonewaiter.fixture.authentication.AuthenticationAttemptCountBuilder;
import com.everyonewaiter.fixture.authentication.AuthenticationAttemptCountKeyBuilder;

@Import(AuthenticationAttemptCountRedisAdapter.class)
class AuthenticationAttemptCountRedisAdapterTest extends RedisAdapterTest {

	@Autowired
	AuthenticationAttemptCountRedisAdapter authenticationAttemptCountRedisAdapter;

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@DisplayName("인증 시도 횟수를 생성한다.")
	@Test
	void create() {
		AuthenticationAttemptCount authenticationAttemptCount = new AuthenticationAttemptCountBuilder().build();
		AuthenticationAttemptCountKey key = authenticationAttemptCount.getKey();

		authenticationAttemptCountRedisAdapter.create(authenticationAttemptCount);

		String actual = getAuthenticationAttemptCountValue(key);
		assertThat(actual).isEqualTo(authenticationAttemptCount.getCount().toString());
	}

	@DisplayName("인증 시도 횟수를 조회한다.")
	@Test
	void find() {
		AuthenticationAttemptCountKey key = new AuthenticationAttemptCountKeyBuilder().build();
		redisTemplate.opsForValue().set(key.getValue(), String.valueOf(1));

		Optional<AuthenticationAttemptCount> actual = authenticationAttemptCountRedisAdapter.find(key);

		assertThat(actual).isPresent();
		assertThat(actual.get().getCount()).isEqualTo(1);
	}

	@DisplayName("인증 시도 횟수를 조회하지 못하면 비어있는 Optional을 반환한다.")
	@Test
	void findEmpty() {
		AuthenticationAttemptCountKey key = new AuthenticationAttemptCountKeyBuilder().build();

		Optional<AuthenticationAttemptCount> actual = authenticationAttemptCountRedisAdapter.find(key);

		assertThat(actual).isEmpty();
	}

	@DisplayName("인증 시도 횟수를 증가시킨다.")
	@Test
	void increment() {
		AuthenticationAttemptCountKey key = new AuthenticationAttemptCountKeyBuilder().build();
		redisTemplate.opsForValue().set(key.getValue(), String.valueOf(10));

		authenticationAttemptCountRedisAdapter.increment(key);

		String actual = getAuthenticationAttemptCountValue(key);
		assertThat(actual).isEqualTo(String.valueOf(11));
	}

	private String getAuthenticationAttemptCountValue(AuthenticationAttemptCountKey authenticationAttemptCountKey) {
		return redisTemplate.opsForValue().get(authenticationAttemptCountKey.getValue());
	}
}
