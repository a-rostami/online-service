package com.rostami.onlineservice.service.security;

import com.rostami.onlineservice.model.Admin;
import com.rostami.onlineservice.model.security.UserDetailsImpl;
import com.rostami.onlineservice.repository.AdminRepository;
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
        if (admin != null)
            return new UserDetailsImpl(admin);

        else  {
            try {
                return customerDetailsService.loadUserByUsername(username);
            }
            catch (UsernameNotFoundException ex){
                try{
                    return expertDetailsService.loadUserByUsername(username);
                }
                catch (UsernameNotFoundException e){
                    throw new UsernameNotFoundException("There Is No Admin With This Id;");
                }
            }
        }
    }
}
