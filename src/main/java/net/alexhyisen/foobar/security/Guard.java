package net.alexhyisen.foobar.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class Guard {
    public boolean check(Authentication authentication, long uidInPath) {
        final String name = authentication.getName();
        if ("anonymousUser".equals(name)) {
            return false;
        }
        final long uidInAuth = Long.valueOf(name);
        return uidInAuth == uidInPath;
    }
}
