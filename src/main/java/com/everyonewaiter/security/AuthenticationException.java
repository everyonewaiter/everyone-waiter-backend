package com.everyonewaiter.security;

public class AuthenticationException extends RuntimeException {

	public AuthenticationException() {
		super("require.authentication");
	}
}
