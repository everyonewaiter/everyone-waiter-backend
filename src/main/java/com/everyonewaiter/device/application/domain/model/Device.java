package com.everyonewaiter.device.application.domain.model;

import com.everyonewaiter.security.SecureEncodable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Device implements SecureEncodable {

	private DevicePurpose purpose;
	private DeviceStatus status;
	private DeviceAccessKey accessKey;

	public boolean isNotPos() {
		return purpose != DevicePurpose.POS;
	}

	public boolean isInactive() {
		return status == DeviceStatus.INACTIVE;
	}

	@Override
	public String getSecretKey() {
		return "";
	}

	@Override
	public String getRawText() {
		return "";
	}
}
