package com.one.mongoreactivehumanresources.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.one.mongoreactivehumanresources.documents.User;

import java.time.LocalDateTime;
import java.util.Arrays;

@JsonInclude(Include.NON_NULL)
public class UserDto extends UserMinimumDto {
    private String email;

    private Boolean active;

    private LocalDateTime registrationDate;

    public UserDto() {
    }

    public UserDto(User user) {
        super(user.getMobile(), user.getUsername(), user.getRoles());
        this.email = user.getEmail();
        this.active = user.isActive();
        this.registrationDate = user.getRegistrationDate();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "mobile='" + this.getMobile() + '\'' +
                ", username='" + this.getUsername() + '\'' +
                ", email='" + email + '\'' +
                ", active=" + active +
                ", roles=" + Arrays.toString(this.getRoles()) +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
