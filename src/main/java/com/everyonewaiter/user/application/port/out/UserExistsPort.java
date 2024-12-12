package com.everyonewaiter.user.application.port.out;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.PhoneNumber;

public interface UserExistsPort {

	boolean exists(Email email);

	boolean exists(PhoneNumber phoneNumber);
}
