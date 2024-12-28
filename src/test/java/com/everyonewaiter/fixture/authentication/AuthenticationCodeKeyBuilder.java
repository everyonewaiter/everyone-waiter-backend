package com.everyonewaiter.fixture.authentication;

import com.everyonewaiter.authentication.application.domain.model.AuthenticationCodeKey;
import com.everyonewaiter.authentication.application.domain.model.AuthenticationPurpose;
import com.everyonewaiter.fixture.user.PhoneNumberBuilder;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public class AuthenticationCodeKeyBuilder {

	private final PhoneNumber phoneNumber = new PhoneNumberBuilder().build();
	private final AuthenticationPurpose purpose = AuthenticationPurpose.USER_SIGN_UP;

	public AuthenticationCodeKey build() {
		return new AuthenticationCodeKey(phoneNumber, purpose);
	}
}
