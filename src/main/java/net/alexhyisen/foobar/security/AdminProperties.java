package net.alexhyisen.foobar.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "foobar.admin")
public class AdminProperties {
    private boolean enabled;
    private String username;
    private String password;
    private String nickname;
    private String uid;
}
