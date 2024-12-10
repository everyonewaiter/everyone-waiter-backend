package com.everyonewaiter.user.application.port.out;

import java.util.Optional;

import com.everyonewaiter.user.application.domain.model.Email;
import com.everyonewaiter.user.application.domain.model.User;

public interface LoadUserPort {

	Optional<User> loadUser(Email email);
}
