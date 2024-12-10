package com.everyonewaiter.fixture.security;

import com.everyonewaiter.security.Decodable;

public class DecodableBuilder {

	private String encodedText = "{encode}RAW_TEXT";

	public Decodable build() {
		return () -> encodedText;
	}

	public DecodableBuilder setEncodedText(String encodedText) {
		this.encodedText = encodedText;
		return this;
	}
}
