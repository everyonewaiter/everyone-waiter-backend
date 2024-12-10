package com.everyonewaiter.security;

record Signature(String value) implements Decodable {

	@Override
	public String getEncodedText() {
		return value;
	}
}
