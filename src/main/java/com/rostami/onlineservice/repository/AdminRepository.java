package com.rostami.onlineservice.repository;

import com.rostami.onlineservice.model.Admin;
import com.rostami.onlineservice.repository.base.BaseRepository;

import java.util.Optional;

public interface AdminRepository extends BaseRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}
