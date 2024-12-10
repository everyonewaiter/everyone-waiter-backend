package com.everyonewaiter.fixture.user;

import com.everyonewaiter.user.application.domain.model.EncodedPassword;

public class EncodedPasswordBuilder {

	private String encodedPassword = "A!B@C#D\\$E%F^G&H*I(J)K_L+M=N";

	public EncodedPassword build() {
		return new EncodedPassword(encodedPassword);
	}

	public EncodedPasswordBuilder setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
		return this;
	}
}
