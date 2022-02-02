package com.rostami.onlineservice.model.security.enums;

public enum RoleEnum {
    ADMIN("ROLE_ADMIN"),
    CUSTOMER("ROLE_CUSTOMER"),
    EXPERT("ROLE_EXPERT");

    private final String name;

    RoleEnum (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
