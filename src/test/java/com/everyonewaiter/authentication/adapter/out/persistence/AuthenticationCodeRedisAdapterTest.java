package com.everyonewaiter.authentication.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

import com.everyonewaiter.RedisAdapterTest;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationCode;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationCodeKey;
import com.everyonewaiter.fixture.authentication.AuthenticationCodeBuilder;
import com.everyonewaiter.fixture.authentication.AuthenticationCodeKeyBuilder;

@Import(AuthenticationCodeRedisAdapter.class)
class AuthenticationCodeRedisAdapterTest extends RedisAdapterTest {

	@Autowired
	AuthenticationCodeRedisAdapter authenticationCodeRedisAdapter;

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	@DisplayName("인증 번호를 생성한다.")
	@Test
	void create() {
		AuthenticationCode authenticationCode = new AuthenticationCodeBuilder().build();
		AuthenticationCodeKey key = authenticationCode.getKey();

		authenticationCodeRedisAdapter.create(authenticationCode);

		String actual = getAuthenticationCodeValue(key);
		assertThat(actual).isEqualTo(authenticationCode.getCode().toString());
	}

	@DisplayName("인증 번호를 조회한다.")
	@Test
	void find() {
		Integer code = 123456;
		AuthenticationCodeKey key = new AuthenticationCodeKeyBuilder().build();
		redisTemplate.opsForValue().set(key.getValue(), String.valueOf(code));

		AuthenticationCode actual = authenticationCodeRedisAdapter.findOrElseThrowAndDelete(key);

		assertThat(actual.getCode()).isEqualTo(code);
	}

	@DisplayName("인증 번호를 조회하지 못하면 예외가 발생한다.")
	@Test
	void findOrElseThrow() {
		AuthenticationCodeKey key = new AuthenticationCodeKeyBuilder().build();
		assertThatThrownBy(() -> authenticationCodeRedisAdapter.findOrElseThrowAndDelete(key))
			.isInstanceOf(NoSuchElementException.class);
	}

	private String getAuthenticationCodeValue(AuthenticationCodeKey authenticationCodeKey) {
		return redisTemplate.opsForValue().get(authenticationCodeKey.getValue());
	}

	@DisplayName("인증 번호를 조회한 후 삭제한다.")
	@Test
	void findOrElseThrowAndDelete() {
		int code = 123456;
		AuthenticationCodeKey key = new AuthenticationCodeKeyBuilder().build();
		redisTemplate.opsForValue().set(key.getValue(), String.valueOf(code));

		AuthenticationCode authenticationCode = authenticationCodeRedisAdapter.findOrElseThrowAndDelete(key);

		String actual = getAuthenticationCodeValue(key);
		assertThat(actual).isNull();
		assertThat(authenticationCode.getCode()).isEqualTo(code);
	}
}
