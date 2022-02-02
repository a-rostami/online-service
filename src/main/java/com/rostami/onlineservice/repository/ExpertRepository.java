package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.repository.base.BaseRepository;

import java.util.Optional;

public interface ExpertRepository extends BaseRepository<Expert, Long> {
     Optional<Expert> findByEmail(String email);
}
