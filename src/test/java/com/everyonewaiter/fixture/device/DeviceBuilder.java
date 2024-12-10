package com.everyonewaiter.fixture.device;

import com.everyonewaiter.device.application.domain.model.Device;
import com.everyonewaiter.device.application.domain.model.DeviceAccessKey;
import com.everyonewaiter.device.application.domain.model.DevicePurpose;
import com.everyonewaiter.device.application.domain.model.DeviceStatus;

public class DeviceBuilder {

	private DevicePurpose purpose = DevicePurpose.POS;
	private DeviceStatus status = DeviceStatus.ACTIVE;
	private final DeviceAccessKey accessKey = new DeviceAccessKey();

	public Device build() {
		return new Device(purpose, status, accessKey);
	}

	public DeviceBuilder setPurpose(DevicePurpose purpose) {
		this.purpose = purpose;
		return this;
	}

	public DeviceBuilder setStatus(DeviceStatus status) {
		this.status = status;
		return this;
	}
}
