package com.everyonewaiter.authentication.adapter.out.persistence;

import static com.everyonewaiter.common.ExceptionMessageFormatter.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCount;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationAttemptCountKey;
import com.everyonewaiter.authentication.application.port.out.AuthenticationAttemptCountCreatePort;
import com.everyonewaiter.authentication.application.port.out.AuthenticationAttemptCountFindPort;
import com.everyonewaiter.authentication.application.port.out.AuthenticationAttemptCountIncrementPort;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class AuthenticationAttemptCountRedisAdapter implements
	AuthenticationAttemptCountCreatePort, AuthenticationAttemptCountFindPort, AuthenticationAttemptCountIncrementPort {

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void create(AuthenticationAttemptCount authenticationAttemptCount) {
		String key = authenticationAttemptCount.getKey().getValue();
		String value = authenticationAttemptCount.getCount().toString();
		Duration expiration = Duration.ofSeconds(authenticationAttemptCount.getExpirationSeconds());
		redisTemplate.opsForValue().set(key, value, expiration);
	}

	@Override
	public Optional<AuthenticationAttemptCount> find(AuthenticationAttemptCountKey key) {
		return Optional.ofNullable(redisTemplate.opsForValue().get(key.getValue()))
			.map(count -> mapToDomain(key, count));
	}

	private AuthenticationAttemptCount mapToDomain(AuthenticationAttemptCountKey key, String count) {
		try {
			return new AuthenticationAttemptCount(key.phoneNumber(), key.purpose(), Integer.valueOf(count));
		} catch (NumberFormatException exception) {
			redisTemplate.delete(key.getValue());
			String message =
				format("invalid.authentication.attempt.type", List.of(key, count), List.of("Key", "Value"));
			throw new IllegalStateException(message);
		}
	}

	@Override
	public void increment(AuthenticationAttemptCountKey key) {
		redisTemplate.opsForValue().increment(key.getValue());
	}
}
