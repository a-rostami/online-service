
package com.rostami.onlinehomeservices.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AdStatus {
    @JsonProperty("waiting-for-offer")
    WAITING_FOR_OFFER,
    @JsonProperty("started")
    STARTED,
    @JsonProperty("done")
    DONE,
    @JsonProperty("paid")
    PAID
}
