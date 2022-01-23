package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Ad;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends BaseRepository<Ad, Long> {
}
