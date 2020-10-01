package com.one.mongoreactivehumanresources.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Document
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String mobile;
    private String username;
    private LocalDateTime registrationDate;
    private String password;
    private Boolean active;
    private String email;
    private Role[] roles;

    public User() {
        this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
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

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password == null) {
            this.password = UUID.randomUUID().toString();
        } else {
            this.password = new BCryptPasswordEncoder().encode(password);
        }
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        return this.mobile.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || obj != null && getClass() == obj.getClass() && mobile.equals(((User) obj).mobile);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", mobile='" + mobile + '\'' +
                ", username='" + username + '\'' +
                ", registrationDate=" + registrationDate +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", email='" + email + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }

    public static class Builder {
        private User user;

        private Builder() {
            this.user = new User();
            this.user.setPassword(null);
            this.user.roles = new Role[]{Role.CUSTOMER};
        }

        public Builder mobile(String mobile) {
            this.user.mobile = mobile;
            return this;
        }

        public Builder username(String username) {
            this.user.username = username;
            return this;
        }

        public Builder registrationDate(LocalDateTime registrationDate) {
            this.user.registrationDate = registrationDate;
            return this;
        }

        public Builder password(String password) {
            this.user.setPassword(password);
            return this;
        }

        public Builder active(Boolean active) {
            this.user.active = active;
            return this;
        }

        public Builder email(String email) {
            this.user.email = email;
            return this;
        }

        public Builder roles(Role... roles) {
            this.user.roles = roles;
            return this;
        }

        public User build() {
            return this.user;
        }
    }
}
