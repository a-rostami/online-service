package com.rostami.onlinehomeservices.service.security;

import com.rostami.onlinehomeservices.model.Expert;
import com.rostami.onlinehomeservices.model.security.UserDetailsImpl;
import com.rostami.onlinehomeservices.repository.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpertDetailsServiceImpl implements UserDetailsService {
    private final ExpertRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Expert expert = repository.findByEmail(username).orElseThrow
                (() -> new UsernameNotFoundException("There Is No Expert With This Username"));

        return new UserDetailsImpl(expert);
    }
}
