package com.rostami.onlineservice.controller.api.filter;

import com.rostami.onlineservice.controller.api.filter.base.UserSpecification;
import com.rostami.onlineservice.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerSpecification extends UserSpecification<Customer> {}
