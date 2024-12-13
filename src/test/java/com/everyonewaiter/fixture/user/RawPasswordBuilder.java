package com.everyonewaiter.fixture.user;

import com.everyonewaiter.user.application.domain.model.RawPassword;

public class RawPasswordBuilder {

	private String rawPassword = "@password1";

	public RawPassword build() {
		return new RawPassword(rawPassword);
	}

	public RawPasswordBuilder setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
		return this;
	}
}
