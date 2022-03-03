package com.rostami.onlinehomeservices.service.bootstrap;

import com.rostami.onlinehomeservices.exception.EntityLoadException;
import com.rostami.onlinehomeservices.model.security.auth.Permission;
import com.rostami.onlinehomeservices.model.security.auth.Role;
import com.rostami.onlinehomeservices.model.security.enums.PermissionEnum;
import com.rostami.onlinehomeservices.repository.security.PermissionRepository;
import com.rostami.onlinehomeservices.repository.security.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.ENTITY_ID_LOAD_MESSAGE;
import static com.rostami.onlinehomeservices.model.security.enums.PermissionEnum.*;
import static com.rostami.onlinehomeservices.model.security.enums.RoleEnum.*;

@Component
@RequiredArgsConstructor
public class SetupAuthorities implements CommandLineRunner {

    public static final Set<Role> SAVED_ROLES = new HashSet<>();

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        if (permissionRepository.count() > 0 && permissionRepository.count() > 0) {
            List<Role> all = roleRepository.findAll();
            SAVED_ROLES.addAll(all);
            return;
        }

        setupPermissions();
        setupAdminPermissions();
        setupCustomerPermissions();
        setupExpertPermissions();
    }

    private void setupAdminPermissions() {
        Role admin = Role.builder().roleEnum(ADMIN).build();
        Set<Permission> adminPermissions = new HashSet<>();
        for (PermissionEnum permissionEnum : allPermissionEnums()) {
            adminPermissions.add(permissionRepository.findByPermissionEnum(permissionEnum)
                    .orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE)));
        }
        admin.setPermissions(adminPermissions);
        Role saved = roleRepository.save(admin);
        SAVED_ROLES.add(saved);
    }

    private void setupPermissions(){
        for (PermissionEnum permissionEnum : allPermissionEnums()){
            permissionRepository.save(Permission.builder().permissionEnum(permissionEnum).build());
        }
    }

    private Set<PermissionEnum> allPermissionEnums() {
        return Set.of(AD_WRITE, AD_READ, CREDIT_WRITE, CREDIT_READ, CUSTOMER_WRITE, CUSTOMER_READ, EXPERT_WRITE, EXPERT_READ,
                MAINSERVICE_WRITE, MAINSERVICE_READ, SUBSERVICE_READ, SUBSERVICE_WRITE, OFFER_READ, OFFER_WRITE,
                OPINION_READ, OPINION_WRITE);
    }

    private void setupCustomerPermissions() {
        Role customer = Role.builder().roleEnum(CUSTOMER).build();
        Set<Permission> customerPermissions = new HashSet<>();
        for (PermissionEnum permissionEnum : allCustomerPermissionEnums()) {
            customerPermissions.add(permissionRepository.findByPermissionEnum(permissionEnum)
                    .orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE)));
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
        Role expert = Role.builder().roleEnum(EXPERT).build();
        Set<Permission> expertPermissions = new HashSet<>();
        for (PermissionEnum permissionEnum : allExpertPermissionEnums()) {
            expertPermissions.add(permissionRepository.findByPermissionEnum(permissionEnum)
                    .orElseThrow(() -> new EntityLoadException(ENTITY_ID_LOAD_MESSAGE)));
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


