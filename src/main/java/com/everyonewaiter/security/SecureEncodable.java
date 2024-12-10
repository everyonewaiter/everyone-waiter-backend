package com.everyonewaiter.security;

public interface SecureEncodable extends Encodable {

	String getSecretKey();
}
