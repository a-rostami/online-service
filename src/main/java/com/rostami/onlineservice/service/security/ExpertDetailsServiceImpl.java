package com.rostami.onlineservice.service.security;

import com.rostami.onlineservice.model.Expert;
import com.rostami.onlineservice.model.security.UserDetailsImpl;
import com.rostami.onlineservice.repository.ExpertRepository;
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
