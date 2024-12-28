package com.everyonewaiter.authentication.application.domain.model;

public interface AuthenticationKey {

	String KEY_FORMAT = "%s:%s:%s";

	String getValue();
}
