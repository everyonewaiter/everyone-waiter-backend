package com.everyonewaiter.fixture.device;

import com.everyonewaiter.device.application.domain.Device;
import com.everyonewaiter.device.application.domain.DeviceAccessKey;
import com.everyonewaiter.device.application.domain.DevicePurpose;
import com.everyonewaiter.device.application.domain.DeviceStatus;

public class DeviceBuilder {

	private DevicePurpose purpose = DevicePurpose.POS;
	private DeviceStatus status = DeviceStatus.ACTIVE;
	private DeviceAccessKey accessKey = new DeviceAccessKey();

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
