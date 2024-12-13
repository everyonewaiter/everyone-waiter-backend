package com.everyonewaiter.user.application.port.out;

import com.everyonewaiter.user.application.domain.model.User;

public interface UserStateUpdatePort {

	void update(User user);
}
