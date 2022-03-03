package com.rostami.onlinehomeservices.repository;

import com.rostami.onlinehomeservices.model.Admin;
import com.rostami.onlinehomeservices.repository.base.BaseRepository;

import java.util.Optional;

public interface AdminRepository extends BaseRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
}
