package com.rostami.onlineservice.controller.api.filter;

import com.rostami.onlineservice.dto.api.filter.CustomerFilter;
import com.rostami.onlineservice.entity.Customer;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerSpecification {
    public static Specification<Customer> getCustomers(CustomerFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (notNull(filter.getFirstname()))
                predicates.add(cb.equal(root.get("firstname"), filter.getFirstname()));

            if (notNull(filter.getLastname()))
                predicates.add(cb.equal(root.get("lastname"), filter.getLastname()));

            if (notNull(filter.getRole()))
                predicates.add(cb.equal(root.get("role"), filter.getRole()));

            if (notNull(filter.getUserStatus()))
                predicates.add(cb.equal(root.get("userStatus"), filter.getUserStatus()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean notNull(String obj){
        return obj != null && !obj.isBlank();
    }

    private static boolean notNull(Object obj){
        return obj != null;
    }
}
