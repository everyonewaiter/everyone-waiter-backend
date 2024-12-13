package com.everyonewaiter.user.application.domain.model;

import static com.everyonewaiter.common.PreconditionChecker.*;

import org.springframework.util.StringUtils;

import com.everyonewaiter.security.Decodable;

public record EncodedPassword(String value) implements Decodable {

	public EncodedPassword {
		require(StringUtils.hasText(value), () -> "require.password.not.blank");
	}

	@Override
	public String getEncodedText() {
		return value;
	}
}
