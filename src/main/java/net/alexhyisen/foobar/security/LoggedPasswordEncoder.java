package net.alexhyisen.foobar.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//This class is designed for debug, and it causes serious security problem,
//I'v written it more than three times, and deleted it in the previous times,
//I do not want to write same code one more time, therefore it's left.
//Do not use it, unless you know what you are doing.
@SuppressWarnings("unused")
public class LoggedPasswordEncoder implements PasswordEncoder {
    private PasswordEncoder realEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        final String encodedPassword = realEncoder.encode(rawPassword);
        System.out.println("encode " + rawPassword + " to " + encodedPassword);
        return encodedPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        final boolean result = realEncoder.matches(rawPassword, encodedPassword);
        System.out.println(result + " = " + rawPassword + " vs " + encodedPassword);
        return result;
    }
}
