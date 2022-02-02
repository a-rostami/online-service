package com.rostami.onlineservice.service.bootstrap;

import com.rostami.onlineservice.model.security.authentication.Permission;
import com.rostami.onlineservice.model.security.authentication.Role;
import com.rostami.onlineservice.model.security.enums.PermissionEnum;
import com.rostami.onlineservice.repository.PermissionRepository;
import com.rostami.onlineservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.rostami.onlineservice.model.security.enums.PermissionEnum.*;
import static com.rostami.onlineservice.model.security.enums.RoleEnum.*;

@Component
@RequiredArgsConstructor
public class SetupAuthorities implements CommandLineRunner {

    public static final Set<Role> SAVED_ROLES = new HashSet<>();

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        if (permissionRepository.count() > 0 && permissionRepository.count() > 0) return;

        setupPermissions();
        setupAdminPermissions();
        setupCustomerPermissions();
        setupExpertPermissions();
    }

    private void setupAdminPermissions() {
        Role admin = Role.builder().name(ADMIN.getName()).build();
        Set<Permission> adminPermissions = new HashSet<>();
        for (PermissionEnum permissionEnum : allPermissionEnums()) {
            adminPermissions.add(permissionRepository.findByName(permissionEnum.getName()));
        }
        admin.setPermissions(adminPermissions);
        Role saved = roleRepository.save(admin);
        SAVED_ROLES.add(saved);
    }

    private void setupPermissions(){
        for (PermissionEnum permissionEnum : allPermissionEnums()){
            permissionRepository.save(Permission.builder().name(permissionEnum.getName()).build());
        }
    }

    private Set<PermissionEnum> allPermissionEnums() {
        return Set.of(AD_WRITE, AD_READ, CREDIT_WRITE, CREDIT_READ, CUSTOMER_WRITE, CUSTOMER_READ, EXPERT_WRITE, EXPERT_READ,
                MAINSERVICE_WRITE, MAINSERVICE_READ, SUBSERVICE_READ, SUBSERVICE_WRITE, OFFER_READ, OFFER_WRITE,
                OPINION_READ, OPINION_WRITE);
    }

    private void setupCustomerPermissions() {
        Role customer = Role.builder().name(CUSTOMER.getName()).build();
        Set<Permission> customerPermissions = new HashSet<>();
        for (PermissionEnum permissionEnum : allCustomerPermissionEnums()) {
            customerPermissions.add(permissionRepository.findByName(permissionEnum.getName()));
        }
        customer.setPermissions(customerPermissions);
        Role saved = roleRepository.save(customer);
        SAVED_ROLES.add(saved);
    }

    private Set<PermissionEnum> allCustomerPermissionEnums() {
        return Set.of(AD_WRITE, AD_READ, CREDIT_READ, CUSTOMER_WRITE, CUSTOMER_READ,
                EXPERT_READ, OFFER_READ, OPINION_READ, OPINION_WRITE);
    }

    private void setupExpertPermissions() {
        Role expert = Role.builder().name(EXPERT.getName()).build();
        Set<Permission> expertPermissions = new HashSet<>();
        for (PermissionEnum permissionEnum : allExpertPermissionEnums()) {
            expertPermissions.add(permissionRepository.findByName(permissionEnum.getName()));
        }
        expert.setPermissions(expertPermissions);
        Role saved = roleRepository.save(expert);
        SAVED_ROLES.add(saved);
    }

    private Set<PermissionEnum> allExpertPermissionEnums() {
        return Set.of(AD_READ, CREDIT_READ,  CUSTOMER_READ, EXPERT_WRITE,
                EXPERT_READ, OFFER_READ, OFFER_WRITE, OPINION_READ);
    }
}


