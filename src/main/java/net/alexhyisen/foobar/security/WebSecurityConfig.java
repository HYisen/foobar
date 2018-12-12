package net.alexhyisen.foobar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final LinkRepository linkRepository;

    @Autowired
    public WebSecurityConfig(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //noinspection SpringElInspection
        http
                .csrf().disable()
                .authorizeRequests()
//                .anyRequest().permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/{uid}/**").access("@guard.check(authentication,#uid)")
                .anyRequest().permitAll()
                .and()
                .formLogin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(new UserService(linkRepository))
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
