package net.alexhyisen.foobar.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Username already exists.")
class UsernameExistsException extends RuntimeException {
}
