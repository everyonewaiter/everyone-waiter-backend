package com.everyonewaiter.security;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AuthenticationResolverConfiguration implements WebMvcConfigurer {

	private final AuthenticationUserResolver authenticationUserResolver;
	private final AuthenticationDeviceResolver authenticationDeviceResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authenticationUserResolver);
		resolvers.add(authenticationDeviceResolver);
	}
}
