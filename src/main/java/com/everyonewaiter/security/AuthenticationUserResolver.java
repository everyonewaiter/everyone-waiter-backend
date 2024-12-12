package com.everyonewaiter.security;

import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserRole;
import com.everyonewaiter.user.application.port.out.UserFindPort;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthenticationUserResolver implements HandlerMethodArgumentResolver {

	private static final String BEARER = "Bearer ";

	private final JwtProvider jwtProvider;
	private final UserFindPort userFindPort;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAuthenticationUserAnnotation = parameter.hasParameterAnnotation(AuthenticationUser.class);
		boolean isCorrectParameterType = User.class.isAssignableFrom(parameter.getParameterType());
		return hasAuthenticationUserAnnotation && isCorrectParameterType;
	}

	@Override
	public User resolveArgument(
		@NonNull MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		@NonNull NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		String accessToken = extractToken(webRequest).orElseThrow(AuthenticationException::new);
		Email email = jwtProvider.decode(accessToken).orElseThrow(AuthenticationException::new);
		User user = userFindPort.find(email).orElseThrow(AuthenticationException::new);
		validateUserAccess(user, parameter);
		return user;
	}

	private Optional<String> extractToken(NativeWebRequest request) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(BEARER)) {
			return Optional.of(authorizationHeader.substring(BEARER.length()));
		}
		return Optional.empty();
	}

	private void validateUserAccess(User user, MethodParameter parameter) {
		AuthenticationUser annotation = parameter.getParameterAnnotation(AuthenticationUser.class);
		assert annotation != null;

		if (annotation.administrator() && user.lackRole(UserRole.ADMIN)) {
			throw new AccessDeniedException("require.role.admin");
		}
		if (annotation.owner() && user.lackRole(UserRole.OWNER)) {
			throw new AccessDeniedException("require.role.owner");
		}
	}
}
