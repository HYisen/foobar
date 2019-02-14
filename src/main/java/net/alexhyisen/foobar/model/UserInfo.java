package net.alexhyisen.foobar.model;

import lombok.Data;

@Data
public class UserInfo {
    private String oldPassword;
    private String newPassword;
    private String nickname;
}
