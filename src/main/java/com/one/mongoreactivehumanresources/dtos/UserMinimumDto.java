package com.one.mongoreactivehumanresources.dtos;

import com.one.mongoreactivehumanresources.documents.Role;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class UserMinimumDto {

    @NotNull
    private String mobile;

    @NotNull
    private String username;

    @NotNull
    private Role[] roles;

    public UserMinimumDto() {
        this("0000000000","", new Role[]{});
    }

    public UserMinimumDto(String mobile, String username, Role[] roles) {
        this.mobile = mobile;
        this.username = username;
        this.roles = roles;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserMinimumDto{" +
                "mobile='" + mobile + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }
}
