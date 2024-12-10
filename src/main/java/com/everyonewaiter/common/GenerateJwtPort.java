package com.everyonewaiter.common;

import com.everyonewaiter.user.application.domain.Email;

public interface GenerateJwtPort {

	String generate(Email email);

	String generate(Email email, Long expirationMillisecond);
}
