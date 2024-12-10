package com.everyonewaiter.fixture.security;

import java.lang.annotation.Annotation;

import com.everyonewaiter.security.AuthenticationDevice;

public class AuthenticationDeviceBuilder {

	private boolean pos = false;

	public AuthenticationDevice build() {
		return new AuthenticationDevice() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return AuthenticationDevice.class;
			}

			@Override
			public boolean pos() {
				return pos;
			}
		};
	}

	public AuthenticationDeviceBuilder setPos(boolean pos) {
		this.pos = pos;
		return this;
	}
}
