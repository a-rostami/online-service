package com.rostami.onlineservice.dto.api.filter;

import com.rostami.onlineservice.entity.enums.Role;
import com.rostami.onlineservice.entity.enums.UserStatus;
import lombok.Data;

@Data
public class CustomerFilter {
    private String firstname;
    private String lastname;
    private Role role;
    private UserStatus userStatus;
}
