package com.everyonewaiter.fixture.security;

import com.everyonewaiter.security.SecureEncodable;

public class SecureEncodableBuilder extends EncodableBuilder {

	private String secretKey = "SECRET_KEY";

	@Override
	public SecureEncodable build() {
		return new SecureEncodable() {

			@Override
			public String getRawText() {
				return rawText;
			}

			@Override
			public String getSecretKey() {
				return secretKey;
			}
		};
	}

	@Override
	public SecureEncodableBuilder setRawText(String rawText) {
		super.setRawText(rawText);
		return this;
	}

	public SecureEncodableBuilder setSecretKey(String secretKey) {
		this.secretKey = secretKey;
		return this;
	}
}
