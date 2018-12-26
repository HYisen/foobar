package net.alexhyisen.foobar.module;

import lombok.Data;

@Data
public class UserInfo {
    private String oldPassword;
    private String newPassword;
    private String nickname;
}
