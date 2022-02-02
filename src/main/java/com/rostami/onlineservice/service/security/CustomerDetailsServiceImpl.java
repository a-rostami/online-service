package com.rostami.onlineservice.service.security;

import com.rostami.onlineservice.model.Customer;
import com.rostami.onlineservice.model.security.UserDetailsImpl;
import com.rostami.onlineservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = repository.findByEmail(username).orElseThrow
                (() -> new UsernameNotFoundException("There Is No Customer With This Username"));

        return new UserDetailsImpl(customer);
    }
}
