package com.rostami.onlineservice.entity.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role {
    @JsonProperty("admin")
    ADMIN,
    @JsonProperty("customer")
    CUSTOMER,
    @JsonProperty("expert")
    EXPERT
}