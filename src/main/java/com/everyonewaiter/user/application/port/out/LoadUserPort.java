package com.everyonewaiter.user.application.port.out;

import com.everyonewaiter.user.application.domain.Email;
import com.everyonewaiter.user.application.domain.User;

public interface LoadUserPort {

	User loadUser(Email email);
}
