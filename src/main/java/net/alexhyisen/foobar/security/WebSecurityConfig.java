package net.alexhyisen.foobar.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final LinkRepository linkRepository;
    private final AdminProperties adminProperties;
    @Value("${foobar.enableSecurity}")
    private boolean enableSecurity;
    @Value("${foobar.enableCsrfProtection}")
    private boolean enableCsrfProtection;

    @Autowired
    public WebSecurityConfig(LinkRepository linkRepository, AdminProperties adminProperties) {
        this.linkRepository = linkRepository;
        this.adminProperties = adminProperties;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!enableSecurity) {
            http.authorizeRequests().anyRequest().permitAll();
        }
        if (!enableCsrfProtection) {
            http.csrf().disable();
        }

        //noinspection SpringElInspection
        http
                .authorizeRequests()
                .antMatchers("/dashboard").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                .antMatchers(HttpMethod.GET, "/api/{uid}/paper").permitAll()//Should I limit it to logined users?
                .antMatchers("/api/{uid}/**").access("@guard.check(authentication,#uid)")
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/moments")
                .and()
                .logout().logoutSuccessUrl("/login").logoutUrl("/logout");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(new UserService(linkRepository, passwordEncoder(), adminProperties))
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
