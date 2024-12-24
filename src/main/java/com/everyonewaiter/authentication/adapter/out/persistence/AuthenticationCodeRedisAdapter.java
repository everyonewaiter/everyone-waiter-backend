package com.everyonewaiter.authentication.adapter.out.persistence;

import static com.everyonewaiter.common.ExceptionMessageFormatter.*;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationCode;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationCodeKey;
import com.everyonewaiter.authentication.application.port.out.AuthenticationCodeCreatePort;
import com.everyonewaiter.authentication.application.port.out.AuthenticationCodeFindPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class AuthenticationCodeRedisAdapter implements AuthenticationCodeCreatePort, AuthenticationCodeFindPort {

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void create(AuthenticationCode authenticationCode) {
		String key = authenticationCode.getKey().getValue();
		String value = authenticationCode.getCode().toString();
		Duration expiration = Duration.ofSeconds(authenticationCode.getExpirationSeconds());
		redisTemplate.opsForValue().set(key, value, expiration);
	}

	@Override
	public AuthenticationCode findOrElseThrowAndDelete(AuthenticationCodeKey key) {
		AuthenticationCode authenticationCode = Optional.ofNullable(redisTemplate.opsForValue().get(key.getValue()))
			.map(code -> mapToDomain(key, code))
			.orElseThrow(() -> new NoSuchElementException(format("not.found.authentication.code", key, "Key")));
		redisTemplate.delete(key.getValue());
		return authenticationCode;
	}

	private AuthenticationCode mapToDomain(AuthenticationCodeKey key, String code) {
		try {
			return new AuthenticationCode(key.phoneNumber(), key.purpose(), Integer.valueOf(code));
		} catch (NumberFormatException exception) {
			redisTemplate.delete(key.getValue());
			String message = format("invalid.authentication.code.type", List.of(key, code), List.of("Key", "Value"));
			throw new IllegalStateException(message);
		}
	}
}
