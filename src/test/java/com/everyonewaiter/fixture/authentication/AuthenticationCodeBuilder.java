package com.everyonewaiter.fixture.authentication;

import java.time.Duration;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationCode;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public class AuthenticationCodeBuilder {

	private PhoneNumber phoneNumber = new PhoneNumberBuilder().build();
	private AuthenticationPurpose purpose = AuthenticationPurpose.USER_SIGN_UP;
	private Integer code = 123456;
	private Long expirationSeconds = Duration.ofMinutes(5L).getSeconds();

	public AuthenticationCode build() {
		return new AuthenticationCode(phoneNumber, purpose, code, expirationSeconds);
	}

	public AuthenticationCodeBuilder setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public AuthenticationCodeBuilder setPurpose(AuthenticationPurpose purpose) {
		this.purpose = purpose;
		return this;
	}

	public AuthenticationCodeBuilder setCode(Integer code) {
		this.code = code;
		return this;
	}

	public AuthenticationCodeBuilder setExpiration(Long seconds) {
		this.expirationSeconds = seconds;
		return this;
	}
}
