package com.everyonewaiter.common;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionMessageFormatter {

	private static final String EXCEPTION_MESSAGE_FORMAT = "%s {%s} [%s]";

	public static String format(String message, Object argument) {
		return format(message, List.of(argument));
	}

	public static String format(String message, Object argument, String argumentDetail) {
		return format(message, List.of(argument), List.of(argumentDetail));
	}

	public static String format(String message, List<?> arguments) {
		List<String> argumentDetails = arguments.stream()
			.map(argument -> argument.getClass().getSimpleName())
			.toList();
		return format(message, arguments, argumentDetails);
	}

	public static String format(String message, List<?> arguments, List<String> argumentDetails) {
		return EXCEPTION_MESSAGE_FORMAT.formatted(
			message,
			String.join(", ", arguments.stream().map(Object::toString).toList()),
			String.join(", ", argumentDetails)
		);
	}
}
