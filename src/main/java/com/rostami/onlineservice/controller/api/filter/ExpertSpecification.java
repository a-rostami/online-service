package com.rostami.onlineservice.controller.api.filter;

import com.rostami.onlineservice.controller.api.filter.base.UserSpecification;
import com.rostami.onlineservice.dto.api.filter.ExpertFilter;
import com.rostami.onlineservice.dto.api.filter.base.UserFilter;
import com.rostami.onlineservice.entity.Expert;
import com.rostami.onlineservice.entity.SubServ;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class ExpertSpecification extends UserSpecification<Expert> {

    public Specification<Expert> getUsers(ExpertFilter filter) {
        if (filter.getSubServId() == null)
            return super.getUsers(filter);

        Specification<Expert> specification = super.getUsers(filter);
        return specification.and((root, query, cb) -> {
            Root<SubServ> subServRoot = query.from(SubServ.class);
            Expression<List<Expert>> subServExperts = subServRoot.get("experts");
            return cb.and(cb.equal(subServRoot.get("id"), filter.getSubServId()), cb.isMember(root, subServExperts));
        });
    }
}
