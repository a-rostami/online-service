package com.rostami.onlineservice.model.security.enums;

public enum PermissionEnum {
    SUBSERVICE_READ("subservice:read"),
    SUBSERVICE_WRITE("subservice:write"),
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write"),
    EXPERT_READ("expert:read"),
    EXPERT_WRITE("expert:write"),
    OFFER_READ("offer:read"),
    OFFER_WRITE("offer:write"),
    AD_READ("ad:read"),
    AD_WRITE("ad:write"),
    MAINSERVICE_READ("mainservice:read"),
    MAINSERVICE_WRITE("mainservice:write"),
    OPINION_READ("opinion:read"),
    OPINION_WRITE("opinion:write"),
    CREDIT_READ("credit:read"),
    CREDIT_WRITE("credit:write");

    private final String name;

    PermissionEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
