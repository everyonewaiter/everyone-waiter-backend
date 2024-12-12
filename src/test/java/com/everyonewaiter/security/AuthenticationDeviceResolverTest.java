package com.everyonewaiter.security;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.request.NativeWebRequest;

import com.everyonewaiter.device.application.domain.model.Device;
import com.everyonewaiter.device.application.domain.model.DeviceAccessKey;
import com.everyonewaiter.device.application.domain.model.DevicePurpose;
import com.everyonewaiter.device.application.domain.model.DeviceStatus;
import com.everyonewaiter.device.application.port.out.DeviceFindPort;
import com.everyonewaiter.fixture.device.DeviceBuilder;
import com.everyonewaiter.fixture.security.AuthenticationDeviceBuilder;

@ExtendWith(MockitoExtension.class)
class AuthenticationDeviceResolverTest {

	private static final String ACCESS_KEY = "ACCESS_KEY";
	private static final String SIGNATURE = "SIGNATURE";
	private static final String ACCESS_KEY_HEADER = "x-ew-access-key";
	private static final String SIGNATURE_HEADER = "x-ew-signature";

	@Mock
	SignatureEncoder signatureEncoder;

	@Mock
	DeviceFindPort deviceFindPort;

	@Mock
	NativeWebRequest request;

	@Mock
	MethodParameter parameter;

	@InjectMocks
	AuthenticationDeviceResolver authenticationDeviceResolver;

	@DisplayName("파라미터의 어노테이션 지원 여부를 검사한다.")
	@MethodSource("supportParameterArgs")
	@ParameterizedTest(name = "메서드 명: {0}, 결과: {1}")
	void supportParameter(String methodName, boolean expected) throws NoSuchMethodException {
		Method method = TestAuthenticationDeviceController.class.getDeclaredMethod(methodName, Device.class);
		MethodParameter methodParameter = MethodParameter.forExecutable(method, 0);
		assertThat(authenticationDeviceResolver.supportsParameter(methodParameter)).isEqualTo(expected);
	}

	static Stream<Arguments> supportParameterArgs() {
		return Stream.of(
			Arguments.of("device", true),
			Arguments.of("pos", true),
			Arguments.of("anonymous", false)
		);
	}

	@DisplayName("모든 유효성 검사를 통과하면 디바이스 인스턴스를 반환한다.")
	@MethodSource("resolveDeviceArgs")
	@ParameterizedTest(name = "[{index}] => 권한: {0}")
	void resolveDevice(DevicePurpose purpose, AuthenticationDevice authenticationDevice) {
		Device device = new DeviceBuilder().setPurpose(purpose).build();

		when(request.getHeader(ACCESS_KEY_HEADER)).thenReturn(ACCESS_KEY);
		when(request.getHeader(SIGNATURE_HEADER)).thenReturn(SIGNATURE);
		when(signatureEncoder.matches(any(), any())).thenReturn(true);
		when(deviceFindPort.find(any(DeviceAccessKey.class))).thenReturn(Optional.of(device));
		when(parameter.getParameterAnnotation(AuthenticationDevice.class)).thenReturn(authenticationDevice);

		Device actual = authenticationDeviceResolver.resolveArgument(parameter, null, request, null);

		assertThat(actual).isEqualTo(device);
	}

	static Stream<Arguments> resolveDeviceArgs() {
		return Stream.of(
			Arguments.of(DevicePurpose.WAITING, new AuthenticationDeviceBuilder().build()),
			Arguments.of(DevicePurpose.TABLE, new AuthenticationDeviceBuilder().build()),
			Arguments.of(DevicePurpose.POS, new AuthenticationDeviceBuilder().build())
		);
	}

	@SuppressWarnings("unused")
	@DisplayName("디바이스의 필요 권한 검사에 실패한 경우 권한 예외가 발생한다.")
	@MethodSource("failResolveDeviceArgs")
	@ParameterizedTest(name = "[{index}] => 권한: {0}, 필요 권한: {2}")
	void failResolve(DevicePurpose purpose, AuthenticationDevice authenticationDevice, DevicePurpose permission) {
		Device device = new DeviceBuilder().setPurpose(purpose).build();

		when(request.getHeader(ACCESS_KEY_HEADER)).thenReturn(ACCESS_KEY);
		when(request.getHeader(SIGNATURE_HEADER)).thenReturn(SIGNATURE);
		when(signatureEncoder.matches(any(), any())).thenReturn(true);
		when(deviceFindPort.find(any(DeviceAccessKey.class))).thenReturn(Optional.of(device));
		when(parameter.getParameterAnnotation(AuthenticationDevice.class)).thenReturn(authenticationDevice);

		assertThatThrownBy(() -> authenticationDeviceResolver.resolveArgument(parameter, null, request, null))
			.isInstanceOf(AccessDeniedException.class);
	}

	static Stream<Arguments> failResolveDeviceArgs() {
		DevicePurpose permission = DevicePurpose.POS;
		return Stream.of(
			Arguments.of(DevicePurpose.WAITING, new AuthenticationDeviceBuilder().setPos(true).build(), permission),
			Arguments.of(DevicePurpose.TABLE, new AuthenticationDeviceBuilder().setPos(true).build(), permission)
		);
	}

	@DisplayName("서명을 추출하지 못하면 인증 예외가 발생한다.")
	@CsvSource(value = {"NULL:SIGNATURE", "ACCESS_KEY:NULL"}, delimiter = ':', nullValues = "NULL")
	@ParameterizedTest(name = "[{index}] => 액세스 키: {0}, 서명: {1}")
	void failExtractSignature(String accessKeyValue, String signatureValue) {
		when(request.getHeader(ACCESS_KEY_HEADER)).thenReturn(accessKeyValue);
		when(request.getHeader(SIGNATURE_HEADER)).thenReturn(signatureValue);
		assertThatThrownBy(() -> authenticationDeviceResolver.resolveArgument(parameter, null, request, null))
			.isInstanceOf(AuthenticationException.class);
	}

	@DisplayName("디바이스를 찾지 못한다면 인증 예외가 발생한다.")
	@Test
	void notFoundDevice() {
		when(request.getHeader(ACCESS_KEY_HEADER)).thenReturn(ACCESS_KEY);
		when(request.getHeader(SIGNATURE_HEADER)).thenReturn(SIGNATURE);
		when(deviceFindPort.find(any(DeviceAccessKey.class))).thenReturn(Optional.empty());

		assertThatThrownBy(() -> authenticationDeviceResolver.resolveArgument(parameter, null, request, null))
			.isInstanceOf(AuthenticationException.class);
	}

	@DisplayName("디바이스가 비활성 상태라면 권한 예외가 발생한다.")
	@Test
	void inactiveDevice() {
		Device device = new DeviceBuilder().setStatus(DeviceStatus.INACTIVE).build();

		when(request.getHeader(ACCESS_KEY_HEADER)).thenReturn(ACCESS_KEY);
		when(request.getHeader(SIGNATURE_HEADER)).thenReturn(SIGNATURE);
		when(deviceFindPort.find(any(DeviceAccessKey.class))).thenReturn(Optional.of(device));

		assertThatThrownBy(() -> authenticationDeviceResolver.resolveArgument(parameter, null, request, null))
			.isInstanceOf(AccessDeniedException.class);
	}

	@DisplayName("서명이 일치하지 않는다면 예외가 발생한다.")
	@Test
	void invalidSignature() {
		Device device = new DeviceBuilder().build();

		when(request.getHeader(ACCESS_KEY_HEADER)).thenReturn(ACCESS_KEY);
		when(request.getHeader(SIGNATURE_HEADER)).thenReturn(SIGNATURE);
		when(deviceFindPort.find(any(DeviceAccessKey.class))).thenReturn(Optional.of(device));
		when(signatureEncoder.matches(any(), any())).thenReturn(false);

		assertThatThrownBy(() -> authenticationDeviceResolver.resolveArgument(parameter, null, request, null))
			.isInstanceOf(AuthenticationException.class);
	}

	@SuppressWarnings({"unused", "squid:S1186"})
	static class TestAuthenticationDeviceController {
		void device(@AuthenticationDevice Device device) {
		}

		void pos(@AuthenticationDevice(pos = true) Device device) {
		}

		void anonymous(Device device) {
		}
	}
}
