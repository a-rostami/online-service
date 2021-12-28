package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Long> {
}
