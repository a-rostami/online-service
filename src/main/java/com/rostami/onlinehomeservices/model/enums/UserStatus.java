package com.rostami.onlinehomeservices.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserStatus {
    @JsonProperty("new")
    NEW,
    @JsonProperty("pending")
    PENDING,
    @JsonProperty("pendingAdminToVerify")
    PENDING_ADMIN_TO_VERIFY,
    @JsonProperty("verified")
    VERIFIED
}