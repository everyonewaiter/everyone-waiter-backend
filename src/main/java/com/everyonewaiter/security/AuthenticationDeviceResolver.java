package com.everyonewaiter.security;

import java.util.Optional;

import org.springframework.core.MethodParameter;
import org.springframework.data.util.Pair;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.everyonewaiter.device.application.domain.model.Device;
import com.everyonewaiter.device.application.domain.model.DeviceAccessKey;
import com.everyonewaiter.device.application.port.out.LoadDevicePort;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AuthenticationDeviceResolver implements HandlerMethodArgumentResolver {

	private static final String ACCESS_KEY = "x-ew-access-key";
	private static final String SIGNATURE = "x-ew-signature";

	private final SignatureEncoder signatureEncoder;
	private final LoadDevicePort loadDevicePort;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAuthenticationDeviceAnnotation = parameter.hasParameterAnnotation(AuthenticationDevice.class);
		boolean isCorrectParameterType = Device.class.isAssignableFrom(parameter.getParameterType());
		return hasAuthenticationDeviceAnnotation && isCorrectParameterType;
	}

	@Override
	public Device resolveArgument(
		@NonNull MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		@NonNull NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		Pair<DeviceAccessKey, Signature> pair = extractSignature(webRequest).orElseThrow(AuthenticationException::new);
		DeviceAccessKey accessKey = pair.getFirst();
		Signature signature = pair.getSecond();

		Device device = loadDevicePort.loadDevice(accessKey).orElseThrow(AuthenticationException::new);
		processAuthentication(device, signature, parameter);
		return device;
	}

	private Optional<Pair<DeviceAccessKey, Signature>> extractSignature(NativeWebRequest request) {
		String accessKey = request.getHeader(ACCESS_KEY);
		String signature = request.getHeader(SIGNATURE);
		if (StringUtils.hasText(accessKey) && StringUtils.hasText(signature)) {
			return Optional.of(Pair.of(new DeviceAccessKey(accessKey), new Signature(signature)));
		}
		return Optional.empty();
	}

	private void processAuthentication(Device device, Signature signature, MethodParameter parameter) {
		checkDeviceStatus(device);
		validateSignature(device, signature);
		validateDeviceAccess(device, parameter);
	}

	private void checkDeviceStatus(Device device) {
		if (device.isInactive()) {
			throw new AccessDeniedException("require.subscribe");
		}
	}

	private void validateSignature(Device device, Signature signature) {
		if (!signatureEncoder.matches(device, signature)) {
			throw new AuthenticationException();
		}
	}

	private void validateDeviceAccess(Device device, MethodParameter parameter) {
		AuthenticationDevice annotation = parameter.getParameterAnnotation(AuthenticationDevice.class);
		assert annotation != null;

		if (annotation.pos() && device.isNotPos()) {
			throw new AccessDeniedException("require.pos.device");
		}
	}
}
