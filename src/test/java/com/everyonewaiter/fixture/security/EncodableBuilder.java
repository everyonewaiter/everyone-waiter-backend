package com.everyonewaiter.fixture.security;

import com.everyonewaiter.security.Encodable;

public class EncodableBuilder {

	protected String rawText = "RAW_TEXT";

	public Encodable build() {
		return () -> rawText;
	}

	public EncodableBuilder setRawText(String rawText) {
		this.rawText = rawText;
		return this;
	}
}
