package com.everyonewaiter.device.application.port.out;

import java.util.Optional;

import com.everyonewaiter.device.application.domain.Device;
import com.everyonewaiter.device.application.domain.DeviceAccessKey;

public interface LoadDevicePort {

	Optional<Device> loadDevice(DeviceAccessKey accessKey);
}
