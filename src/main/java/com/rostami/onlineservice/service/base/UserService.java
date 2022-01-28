package com.rostami.onlineservice.service.base;

import com.rostami.onlineservice.dto.in.BaseInDto;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.dto.out.single.CreditFindResult;
import com.rostami.onlineservice.entity.Credit;
import com.rostami.onlineservice.entity.base.User;
import com.rostami.onlineservice.exception.DuplicatedEmailException;
import com.rostami.onlineservice.exception.EntityLoadException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UserService<T extends User, ID extends Long> extends BaseService<T, ID> {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected CreateUpdateResult depositToCredit(T user, BigDecimal amount){
        Credit credit = user.getCredit();
        BigDecimal balance = credit.getBalance();
        credit.setBalance(balance.add(amount));
        user.setCredit(credit);
        T saved = getRepository().save(user);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    @Override
    public CreateUpdateResult saveOrUpdate(BaseInDto<T> dto) {
        T entity = dto.convertToDomain();
        checkEmailExist(entity.getEmail(), entity.getId());
        T saved = getRepository().save(entity);
        return CreateUpdateResult.builder().id(saved.getId()).success(true).build();
    }

    private void checkEmailExist(String email, Long id){
        List<T> byEmail = getRepository().findAll(((root, cq, cb) -> cb.equal(root.get("email"), email)));
        if (id == null  && !CollectionUtils.isEmpty(byEmail))
            throw new DuplicatedEmailException("Email Already Exist!");
    }

    public CreditFindResult loadCredit(ID id){
        T user = getRepository().findById(id).orElseThrow(() -> new EntityLoadException("There Is No User With This ID."));
        Credit credit = user.getCredit();
        return CreditFindResult.builder().build().convertToDto(credit);
    }
}

