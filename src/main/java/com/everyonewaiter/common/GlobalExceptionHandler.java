package com.everyonewaiter.common;

import static com.everyonewaiter.common.ExceptionMessageFormatter.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.everyonewaiter.security.AuthenticationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
class GlobalExceptionHandler {

	private static final String LOG_FORMAT = "[{}] [{}] {} {}: {}";

	private final MessageSource messageSource;

	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
	ResponseEntity<ErrorResponse> handleBadRequest(HttpServletRequest request, Exception exception) {
		MessagePart messagePart = new MessagePart(exception.getMessage());
		log(request, HttpStatus.BAD_REQUEST, messagePart.getLogMessage(messageSource), exception);
		return ResponseEntity
			.badRequest()
			.body(new ErrorResponse(messagePart.getLocalizedMessage(messageSource, request.getLocale())));
	}

	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleNotFound(HttpServletRequest request, NoSuchElementException exception) {
		MessagePart messagePart = new MessagePart(exception.getMessage());
		log(request, HttpStatus.NOT_FOUND, messagePart.getLogMessage(messageSource), exception);
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new ErrorResponse(messagePart.getLocalizedMessage(messageSource, request.getLocale())));
	}

	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleNotFound(HttpServletRequest request, NoResourceFoundException exception) {
		MessagePart messagePart = new MessagePart("check.resource.path");
		log(request, HttpStatus.NOT_FOUND, messagePart.getLogMessage(messageSource), exception);
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new ErrorResponse(messagePart.getLocalizedMessage(messageSource, request.getLocale())));
	}

	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleMethodNotAllowed(
		HttpServletRequest request,
		HttpRequestMethodNotSupportedException exception
	) {
		MessagePart messagePart = new MessagePart(format(
			"request.method.not.support",
			List.of(request.getRequestURI(), exception.getMethod()),
			List.of("requestURI", "Method")
		));
		log(request, HttpStatus.METHOD_NOT_ALLOWED, messagePart.getLogMessage(messageSource), exception);
		return ResponseEntity
			.status(HttpStatus.METHOD_NOT_ALLOWED)
			.body(new ErrorResponse(messagePart.getLocalizedMessage(messageSource, request.getLocale())));
	}

	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleBadCredentials(HttpServletRequest request, BadCredentialsException exception) {
		MessagePart messagePart = new MessagePart("check.email.password");
		log(request, HttpStatus.UNAUTHORIZED, messagePart.getLogMessage(messageSource), exception);
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(new ErrorResponse(messagePart.getLocalizedMessage(messageSource, request.getLocale())));
	}

	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleAuthentication(HttpServletRequest request, AuthenticationException exception) {
		MessagePart messagePart = new MessagePart(exception.getMessage());
		log(request, HttpStatus.UNAUTHORIZED, messagePart.getLogMessage(messageSource), exception);
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(new ErrorResponse(messagePart.getLocalizedMessage(messageSource, request.getLocale())));
	}

	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleAccountExpired(HttpServletRequest request, AccountExpiredException exception) {
		MessagePart messagePart = new MessagePart("require.email.authentication");
		log(request, HttpStatus.UNAUTHORIZED, messagePart.getLogMessage(messageSource), exception);
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(new ErrorResponse(messagePart.getLocalizedMessage(messageSource, request.getLocale())));
	}

	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleAccountDisabled(HttpServletRequest request, DisabledException exception) {
		MessagePart messagePart = new MessagePart("account.canceled");
		log(request, HttpStatus.UNAUTHORIZED, messagePart.getLogMessage(messageSource), exception);
		return ResponseEntity
			.status(HttpStatus.UNAUTHORIZED)
			.body(new ErrorResponse(messagePart.getLocalizedMessage(messageSource, request.getLocale())));
	}

	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleAccessDenied(HttpServletRequest request, AccessDeniedException exception) {
		MessagePart messagePart = new MessagePart(exception.getMessage());
		log(request, HttpStatus.FORBIDDEN, messagePart.getLogMessage(messageSource), exception);
		return ResponseEntity
			.status(HttpStatus.FORBIDDEN)
			.body(new ErrorResponse(messagePart.getLocalizedMessage(messageSource, request.getLocale())));
	}

	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleInternalServerError(HttpServletRequest request, Exception exception) {
		MessagePart messagePart = new MessagePart("internal.server.error");
		log(request, exception);
		return ResponseEntity
			.internalServerError()
			.body(new ErrorResponse(messagePart.getLocalizedMessage(messageSource, request.getLocale())));
	}

	private void log(HttpServletRequest request, HttpStatus status, String message, Exception exception) {
		String threadName = Thread.currentThread().getName();
		String requestUri = request.getRequestURI();
		String exceptionName = exception.getClass().getSimpleName();
		log.warn(LOG_FORMAT, threadName, requestUri, status, exceptionName, message);
	}

	private void log(HttpServletRequest request, Exception exception) {
		String threadName = Thread.currentThread().getName();
		String requestUri = request.getRequestURI();
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String exceptionName = exception.getClass().getSimpleName();
		log.error(LOG_FORMAT, threadName, requestUri, status, exceptionName, exception.getMessage());
		log.error("Caused by: ", exception);
	}

	static class MessagePart {

		private static final char ARGUMENT_START_BRACKET = '{';
		private static final char ARGUMENT_END_BRACKET = '}';
		private static final char SPACE = ' ';
		private static final char TYPE_START_BRACKET = '[';
		private static final char TYPE_END_BRACKET = ']';
		private static final String DELIMITER = ",";
		private static final String DEFAULT_MESSAGE = "not.found.exception.message";
		private static final Locale DEFAULT_LOCALE = Locale.KOREA;

		private final String message;
		private final String[] arguments;
		private final String[] types;

		MessagePart(String message) {
			this.message = initializeMessage(message);
			this.arguments = extractSection(ARGUMENT_START_BRACKET, ARGUMENT_END_BRACKET);
			this.types = extractSection(TYPE_START_BRACKET, TYPE_END_BRACKET);
		}

		String getLocalizedMessage(MessageSource messageSource, Locale locale) {
			return hasArguments()
				? messageSource.getMessage(message, arguments, message, locale)
				: messageSource.getMessage(message, null, message, locale);
		}

		String getLogMessage(MessageSource messageSource) {
			String localizedMessage = getLocalizedMessage(messageSource, DEFAULT_LOCALE);
			return (localizedMessage + SPACE + mapToArgumentTypes()).trim();
		}

		private String initializeMessage(String message) {
			return message == null ? DEFAULT_MESSAGE : extractMessage(message);
		}

		private String extractMessage(String message) {
			return message.contains(String.valueOf(SPACE)) ? message.substring(0, message.indexOf(SPACE)) : message;
		}

		private String[] extractSection(char start, char end) {
			int startIndex = message.indexOf(start);
			int endIndex = message.indexOf(end);
			return (startIndex == -1 || endIndex == -1)
				? new String[0]
				: message.substring(startIndex + 1, endIndex).replace(String.valueOf(SPACE), "").split(DELIMITER);
		}

		private String mapToArgumentTypes() {
			if (hasMatchingArguments()) {
				Map<String, String> argumentTypes = new LinkedHashMap<>();
				for (int i = 0; i < arguments.length; i++) {
					argumentTypes.put(types[i], arguments[i]);
				}
				return argumentTypes.toString();
			}
			return "";
		}

		private boolean hasArguments() {
			return arguments.length > 0;
		}

		private boolean hasMatchingArguments() {
			return hasArguments() && (arguments.length == types.length);
		}
	}

	record ErrorResponse(String message) {
	}
}
