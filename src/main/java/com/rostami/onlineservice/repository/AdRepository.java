package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdRepository extends JpaRepository<Ad, Long>{
}
