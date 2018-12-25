package net.alexhyisen.foobar.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class Guard {
    private static final SimpleGrantedAuthority ADMIN_AUTHORITY = new SimpleGrantedAuthority("ROLE_ADMIN");

    public boolean check(Authentication authentication, long uidInPath) {
        final String name = authentication.getName();
        if (authentication.getAuthorities().contains(ADMIN_AUTHORITY)) {
            return true;
        }
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
