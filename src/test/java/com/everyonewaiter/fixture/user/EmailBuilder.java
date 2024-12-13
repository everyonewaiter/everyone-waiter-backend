package com.everyonewaiter.fixture.user;

import com.everyonewaiter.user.application.domain.model.Email;

public class EmailBuilder {

	private String email = "handwoong@gmail.com";

	public Email build() {
		return new Email(email);
	}

	public EmailBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
}
