package com.rostami.onlinehomeservices.controller.api.filter;

import com.rostami.onlinehomeservices.controller.api.filter.base.UserSpecification;
import com.rostami.onlinehomeservices.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerSpecification extends UserSpecification<Customer> {}
