package com.everyonewaiter.authentication.application.domain.model;

import com.everyonewaiter.common.AggregateRoot;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = "expirationSeconds", callSuper = false)
public class AuthenticationAttemptCount extends AggregateRoot {

	private final AuthenticationAttemptCountKey key;
	private final PhoneNumber phoneNumber;
	private final AuthenticationPurpose purpose;
	private Integer count;
	private final Long expirationSeconds;

	public AuthenticationAttemptCount(PhoneNumber phoneNumber, AuthenticationPurpose purpose, Integer count) {
		this(phoneNumber, purpose, count, 0L);
	}

	public AuthenticationAttemptCount(PhoneNumber phoneNumber, AuthenticationPurpose purpose, Long expirationSeconds) {
		this(phoneNumber, purpose, 0, expirationSeconds);
	}

	public AuthenticationAttemptCount(
		PhoneNumber phoneNumber,
		AuthenticationPurpose purpose,
		Integer count,
		Long expirationSeconds
	) {
		this.key = new AuthenticationAttemptCountKey(phoneNumber, purpose);
		this.phoneNumber = phoneNumber;
		this.purpose = purpose;
		this.count = count;
		this.expirationSeconds = expirationSeconds;
	}
}
