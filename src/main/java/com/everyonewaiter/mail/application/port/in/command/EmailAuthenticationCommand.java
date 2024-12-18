package com.everyonewaiter.mail.application.port.in.command;

import com.everyonewaiter.user.application.domain.model.Email;

public record EmailAuthenticationCommand(Email email) {
}
