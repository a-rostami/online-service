package com.rostami.onlinehomeservices.service.security;

import com.rostami.onlinehomeservices.model.Admin;
import com.rostami.onlinehomeservices.model.security.UserDetailsImpl;
import com.rostami.onlinehomeservices.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository repository;
    private final CustomerDetailsServiceImpl customerDetailsService;
    private final ExpertDetailsServiceImpl expertDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = repository.findByEmail(username).orElse(null);
        if (admin != null) return new UserDetailsImpl(admin);

        try {
            return customerDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException ex) {
            try {
                return expertDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                throw new UsernameNotFoundException("There Is No Admin With This Id;");
            }
        }
    }
}
