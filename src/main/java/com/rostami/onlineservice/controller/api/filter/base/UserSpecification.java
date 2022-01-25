package com.rostami.onlineservice.controller.api.filter.base;

import com.rostami.onlineservice.dto.api.filter.base.UserFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserSpecification<T> {
    public Specification<T> getUsers(UserFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (notNull(filter.getFirstname()))
                predicates.add(cb.equal(root.get("firstname"), filter.getFirstname()));

            if (notNull(filter.getLastname()))
                predicates.add(cb.equal(root.get("lastname"), filter.getLastname()));

            if (notNull(filter.getEmail()))
                predicates.add(cb.equal(root.get("email"), filter.getEmail()));

            if (notNull(filter.getUserStatus()))
                predicates.add(cb.equal(root.get("userStatus"), filter.getUserStatus()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private boolean notNull(String obj){
        return obj != null && !obj.isBlank();
    }

    private boolean notNull(Object obj){
        return obj != null;
    }
}
