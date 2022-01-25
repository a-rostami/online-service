package com.rostami.onlineservice.controller.api.filter;

import com.rostami.onlineservice.controller.api.filter.base.UserSpecification;
import com.rostami.onlineservice.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerSpecification extends UserSpecification<Customer> {}
