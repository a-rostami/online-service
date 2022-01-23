package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Credit;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends BaseRepository<Credit, Long> {
}
