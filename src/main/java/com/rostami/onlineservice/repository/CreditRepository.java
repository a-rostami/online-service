package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
