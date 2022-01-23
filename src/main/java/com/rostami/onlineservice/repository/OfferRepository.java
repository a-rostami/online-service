package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.entity.Offer;
import com.rostami.onlineservice.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends BaseRepository<Offer, Long> {
}
