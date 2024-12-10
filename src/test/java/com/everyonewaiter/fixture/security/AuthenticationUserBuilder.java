package com.everyonewaiter.fixture.security;

import java.lang.annotation.Annotation;

import com.everyonewaiter.security.AuthenticationUser;

public class AuthenticationUserBuilder {

	private boolean administrator = false;
	private boolean owner = false;

	public AuthenticationUser build() {
		return new AuthenticationUser() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return AuthenticationUser.class;
			}

			@Override
			public boolean administrator() {
				return administrator;
			}

			@Override
			public boolean owner() {
				return owner;
			}
		};
	}

	public AuthenticationUserBuilder setAdministrator(boolean administrator) {
		this.administrator = administrator;
		return this;
	}

	public AuthenticationUserBuilder setOwner(boolean owner) {
		this.owner = owner;
		return this;
	}
}
