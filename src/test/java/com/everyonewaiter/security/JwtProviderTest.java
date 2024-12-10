package com.everyonewaiter.security;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.everyonewaiter.fixture.user.EmailBuilder;
import com.everyonewaiter.user.application.domain.Email;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;

class JwtProviderTest {

	JwtProvider jwtProvider = new JwtProvider(Encoders.BASE64.encode(Jwts.SIG.HS256.key().build().getEncoded()));

	@DisplayName("토큰을 생성하고 생성된 토큰을 불러올 수 있다.")
	@Test
	void generateToken() {
		Email email = new EmailBuilder().build();

		String jwtToken = jwtProvider.generate(email);
		Email actual = jwtProvider.decode(jwtToken).orElseThrow();

		assertThat(actual).isEqualTo(email);
	}

	@DisplayName("유효하지 않은 토큰이라면 비어있는 Optional을 반환한다.")
	@Test
	void invalidToken() {
		assertThat(jwtProvider.decode("INVALID")).isEmpty();
	}

	@DisplayName("토큰이 만료되었다면 비어있는 Optional을 반환한다.")
	@Test
	void expirationToken() {
		Email email = new EmailBuilder().build();

		String jwtToken = jwtProvider.generate(email, -1L);

		assertThat(jwtProvider.decode(jwtToken)).isEmpty();
	}
}
