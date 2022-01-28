package com.rostami.onlineservice.controller.api.filter;

import com.rostami.onlineservice.dto.api.filter.AdFilter;
import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.entity.SubServ;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdSpecification {

    public Specification<Ad> getAds(AdFilter filter){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (notNull(filter.getAdStatus()))
                predicates.add(cb.equal(root.get("status"), filter.getAdStatus()));

            if (notNull(filter.getFromDate()))
                predicates.add(cb.greaterThan(root.get("recordDate"), filter.getFromDate()));

            if (notNull(filter.getSubServId())){
                SubServ subServ = SubServ.builder().id(filter.getSubServId()).build();
                predicates.add(cb.equal(root.get("subServ"), subServ));
            }

            if (notNull(filter.getToDate()))
                predicates.add(cb.lessThan(root.get("recordDate"), filter.getToDate()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private boolean notNull(Object obj){
        return obj != null;
    }
}
