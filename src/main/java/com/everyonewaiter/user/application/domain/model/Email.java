package com.everyonewaiter.user.application.domain.model;

import static com.everyonewaiter.common.ExceptionMessageFormatter.*;
import static com.everyonewaiter.common.PreconditionChecker.*;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public record Email(String value) {

	private static final String EMAIL_REGEX = "^[\\w+-.*]+@[\\w-]+\\.[\\w-.]+$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

	public Email {
		require(StringUtils.hasText(value), () -> "require.email.not.blank");
		require(EMAIL_PATTERN.matcher(value).matches(), () -> format("invalid.email.format", value, "Email"));
	}
}
