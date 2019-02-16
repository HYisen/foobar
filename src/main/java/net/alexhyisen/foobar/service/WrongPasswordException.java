package net.alexhyisen.foobar.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//BadCredentialsException is 302, as a restful API, I want 401.
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "password")
class WrongPasswordException extends RuntimeException{
}
