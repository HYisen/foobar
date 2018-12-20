package net.alexhyisen.foobar.security;

import net.alexhyisen.foobar.module.Link;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final LinkRepository linkRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(LinkRepository linkRepository, PasswordEncoder passwordEncoder) {
        this.linkRepository = linkRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final var link = linkRepository.findByAccount_Username(s);
        final var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(link.getPerson().getUid().toString(), link.getAccount().getPassword(), authorities);
    }

    public Link updatePassword(long uid, String password) {
        return linkRepository.updatePasswordByUid(uid, passwordEncoder.encode(password));
    }
}
