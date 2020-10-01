package com.one.mongoreactivehumanresources.documents;

public enum Role {
    ADMIN, CUSTOMER, AUTHENTICATED;

    public String roleName() {
        return "ROLE_" + this.toString();
    }
}
