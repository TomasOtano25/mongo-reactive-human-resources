package com.one.mongoreactivehumanresources.dtos;

import javax.validation.constraints.NotNull;

public class UserCredentialDto {

    @NotNull
    private String mobile;

    @NotNull String newPassword;

    public UserCredentialDto() {
        this("", "");
    }

    public UserCredentialDto(String mobile, String newPassword) {
        this.mobile = mobile;
        this.newPassword = newPassword;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
