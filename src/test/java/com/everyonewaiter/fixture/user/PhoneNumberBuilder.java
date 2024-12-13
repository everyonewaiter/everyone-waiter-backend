package com.everyonewaiter.fixture.user;

import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public class PhoneNumberBuilder {

	private String phoneNumber = "01012345678";

	public PhoneNumber build() {
		return new PhoneNumber(phoneNumber);
	}

	public PhoneNumberBuilder setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}
}
