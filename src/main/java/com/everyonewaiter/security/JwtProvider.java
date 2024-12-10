package com.everyonewaiter.security;

import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.everyonewaiter.common.GenerateJwtPort;
import com.everyonewaiter.user.application.domain.model.Email;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
class JwtProvider implements GenerateJwtPort {

	private static final Long THREE_HOURS_MILLISECONDS = 1000L * 60L * 60L * 3L;

	private final SecretKey secretKey;

	JwtProvider(@Value("${jwt.secret-key}") String secretKey) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	@Override
	public String generate(Email email) {
		return generate(email, THREE_HOURS_MILLISECONDS);
	}

	@Override
	public String generate(Email email, Long expirationMillisecond) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + expirationMillisecond);
		return Jwts.builder()
			.subject(email.value())
			.issuedAt(now)
			.expiration(expiration)
			.signWith(secretKey)
			.compact();
	}

	public Optional<Email> decode(String token) {
		try {
			Email email = new Email(
				Jwts.parser()
					.verifyWith(secretKey)
					.build()
					.parseSignedClaims(token)
					.getPayload()
					.getSubject()
			);
			return Optional.of(email);
		} catch (Exception exception) {
			return Optional.empty();
		}
	}
}
