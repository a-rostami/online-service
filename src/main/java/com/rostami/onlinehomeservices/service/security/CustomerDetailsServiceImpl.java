package com.rostami.onlinehomeservices.service.security;

import com.rostami.onlinehomeservices.model.Customer;
import com.rostami.onlinehomeservices.model.security.UserDetailsImpl;
import com.rostami.onlinehomeservices.repository.CustomerRepository;
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
