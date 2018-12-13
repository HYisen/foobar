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
        final long uidInAuth;
        try {
            uidInAuth = Long.valueOf(name);
        } catch (NumberFormatException e) {
            System.out.println("failed to parsed name " + name);
            return false;
        }
        return uidInAuth == uidInPath;
    }
}
