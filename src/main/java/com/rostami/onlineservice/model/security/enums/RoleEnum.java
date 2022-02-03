package com.rostami.onlineservice.model.security.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RoleEnum {
    @JsonProperty("ADMIN")
    ADMIN,
    @JsonProperty("CUSTOMER")
    CUSTOMER,
    @JsonProperty("EXPERT")
    EXPERT;
}
