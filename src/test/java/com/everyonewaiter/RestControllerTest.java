package com.everyonewaiter;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import com.everyonewaiter.device.application.domain.model.Device;
import com.everyonewaiter.fixture.device.DeviceBuilder;
import com.everyonewaiter.fixture.user.UserBuilder;
import com.everyonewaiter.security.AuthenticationDevice;
import com.everyonewaiter.security.AuthenticationDeviceResolver;
import com.everyonewaiter.security.AuthenticationException;
import com.everyonewaiter.security.AuthenticationUser;
import com.everyonewaiter.security.AuthenticationUserResolver;
import com.everyonewaiter.user.application.domain.model.User;
import com.everyonewaiter.user.application.domain.model.UserId;
import com.everyonewaiter.user.application.domain.model.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public abstract class RestControllerTest {

	protected static final String VALID_TOKEN = "ACCESS_TOKEN";
	protected static final String VALID_ACCESS_KEY = "ACCESS_KEY";
	protected static final String VALID_SIGNATURE = "SIGNATURE";

	@MockitoBean
	AuthenticationUserResolver authenticationUserResolver;

	@MockitoBean
	AuthenticationDeviceResolver authenticationDeviceResolver;

	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	@BeforeEach
	void setUp(WebApplicationContext context, RestDocumentationContextProvider contextProvider) throws Exception {
		mockMvc = buildMockMvc(context, contextProvider);

		configureSupportsParameter(authenticationUserResolver, AuthenticationUser.class, User.class);
		configureResolveArgument(
			authenticationUserResolver,
			request -> {
				String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
				return StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ");
			},
			() -> new UserBuilder().setId(new UserId(1L)).setRole(UserRole.ADMIN).build());

		configureSupportsParameter(authenticationDeviceResolver, AuthenticationDevice.class, Device.class);
		configureResolveArgument(
			authenticationDeviceResolver,
			request -> {
				boolean hasAccessKey = StringUtils.hasText(request.getHeader("x-ew-access-key"));
				boolean hasSignature = StringUtils.hasText(request.getHeader("x-ew-signature"));
				return hasAccessKey && hasSignature;
			},
			() -> new DeviceBuilder().build());
	}

	private MockMvc buildMockMvc(WebApplicationContext context, RestDocumentationContextProvider contextProvider) {
		return MockMvcBuilders
			.webAppContextSetup(context)
			.addFilter(new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true))
			.alwaysDo(MockMvcResultHandlers.print())
			.apply(
				MockMvcRestDocumentation
					.documentationConfiguration(contextProvider)
					.operationPreprocessors()
					.withRequestDefaults(prettyPrint())
					.withResponseDefaults(prettyPrint())
			).build();
	}

	private void configureSupportsParameter(
		HandlerMethodArgumentResolver resolver,
		Class<? extends Annotation> annotationType,
		Class<?> parameterType
	) {
		when(resolver.supportsParameter(any())).thenAnswer(invocation -> {
			MethodParameter parameter = invocation.getArgument(0);
			return parameter.hasParameterAnnotation(annotationType)
				&& parameterType.isAssignableFrom(parameter.getParameterType());
		});
	}

	private void configureResolveArgument(
		HandlerMethodArgumentResolver resolver,
		Predicate<NativeWebRequest> condition,
		Supplier<Object> supplier
	) throws Exception {
		when(resolver.resolveArgument(any(), any(), any(), any())).thenAnswer(invocation -> {
			NativeWebRequest request = invocation.getArgument(2);
			if (!condition.test(request)) {
				throw new AuthenticationException();
			}
			return supplier.get();
		});
	}

	protected <T> String convertToJsonString(T body) throws Exception {
		return objectMapper.writeValueAsString(body);
	}
}
