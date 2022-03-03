package com.rostami.onlinehomeservices.dto.api.filter.base;

import com.rostami.onlinehomeservices.model.enums.UserStatus;
import lombok.Data;

import java.sql.Date;

@Data
public class UserFilter {
    private String firstname;
    private String lastname;
    private String email;
    private UserStatus userStatus;
    private Date fromRecordDate;
    private Date toRecordDate;
    private Boolean isEnable;
}
