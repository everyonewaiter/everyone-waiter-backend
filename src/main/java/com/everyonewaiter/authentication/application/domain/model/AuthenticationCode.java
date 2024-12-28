package com.everyonewaiter.authentication.application.domain.model;

import org.springframework.util.Assert;

import com.everyonewaiter.common.AggregateRoot;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = "expirationSeconds", callSuper = false)
public class AuthenticationCode extends AggregateRoot {

	private final AuthenticationCodeKey key;
	private final PhoneNumber phoneNumber;
	private final AuthenticationPurpose purpose;
	private final Integer code;
	private final Long expirationSeconds;

	public static AuthenticationCode create(
		PhoneNumber phoneNumber,
		AuthenticationPurpose purpose,
		Integer code,
		Long expirationSeconds
	) {
		AuthenticationCode authenticationCode = new AuthenticationCode(phoneNumber, purpose, code, expirationSeconds);
		authenticationCode.registerEvent(new AuthenticationCodeCreateEvent(phoneNumber, code));
		return authenticationCode;
	}

	public AuthenticationCode(PhoneNumber phoneNumber, AuthenticationPurpose purpose, Integer code) {
		this(phoneNumber, purpose, code, 0L);
	}

	public AuthenticationCode(
		PhoneNumber phoneNumber,
		AuthenticationPurpose purpose,
		Integer code,
		Long expirationSeconds
	) {
		Assert.notNull(code, "AuthenticationCode code must not be null");
		this.key = new AuthenticationCodeKey(phoneNumber, purpose);
		this.phoneNumber = phoneNumber;
		this.purpose = purpose;
		this.code = code;
		this.expirationSeconds = expirationSeconds;
	}
}
