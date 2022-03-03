package com.rostami.onlinehomeservices.service;

import com.rostami.onlinehomeservices.config.util.PasswordEncoder;
import com.rostami.onlinehomeservices.dto.in.update.api.PasswordUpdateParam;
import com.rostami.onlinehomeservices.exception.EntityLoadException;
import com.rostami.onlinehomeservices.exception.WrongPreviousPasswordException;
import com.rostami.onlinehomeservices.model.Admin;
import com.rostami.onlinehomeservices.model.Credit;
import com.rostami.onlinehomeservices.model.enums.UserStatus;
import com.rostami.onlinehomeservices.repository.AdminRepository;
import com.rostami.onlinehomeservices.service.registration.EmailTokenService;
import com.rostami.onlinehomeservices.service.registration.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock private AdminRepository adminRepository;
    @Mock private RegistrationService registrationService;
    @Mock private EmailTokenService emailTokenService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AdminService adminService;

    @BeforeEach
    void setup(){
        bCryptPasswordEncoder = new PasswordEncoder().bCryptPasswordEncoder();
        adminService = new AdminService(adminRepository, registrationService, emailTokenService, bCryptPasswordEncoder);
    }

    @Test
    void test_adminService_changePassword_isOk() {
        // given ----------------------------------------------

        String email = "ahmet@gmail.com";
        String previousPassword = "@Test1234";
        String newPassword = "@Test12345";

        Admin admin = generateAdmin(email, previousPassword);
        PasswordUpdateParam updateParam = generatePasswordUpdateParam(email, newPassword, previousPassword);

        given(adminRepository.findByEmail(email)).willReturn(Optional.of(admin));
        given(adminRepository.save(admin)).willReturn(admin);

        // when ----------------------------------------------

        adminService.changePassword(updateParam);

        // then ----------------------------------------------

        ArgumentCaptor<Admin> argumentCaptor = ArgumentCaptor.forClass(Admin.class);
        verify(adminRepository).save(argumentCaptor.capture());
        Admin fetchedAdmin = argumentCaptor.getValue();

        assertThat(bCryptPasswordEncoder.matches(newPassword, fetchedAdmin.getPassword())).isEqualTo(true);
        assertThat(fetchedAdmin).isEqualTo(admin);
    }

    @Test
    void test_willThrow_ifAdmin_wasNotFound_isOk(){
        // given ----------------------------------------------

        String email = "ahmet@gmail.com";
        String previousPassword = "@Test1234";
        String newPassword = "@Test12345";
        PasswordUpdateParam updateParam = generatePasswordUpdateParam(email, newPassword, previousPassword);

        given(adminRepository.findByEmail(email)).willReturn(Optional.empty());

        // when ----------------------------------------------
        // then ----------------------------------------------

        assertThatThrownBy(() -> adminService.changePassword(updateParam))
                .isInstanceOf(EntityLoadException.class)
                .hasMessage("There Is No Admin With This Email");

    }

    @Test
    void  test_willThrow_ifPreviousPasswords_doesNot_match_isOk(){

        // given ---------------------------------------------

        String email = "ahmet@gmail.com";
        String previousPassword = "@Test1234";
        String newPassword = "@Test12345";

        Admin admin = generateAdmin(email, previousPassword);
        // add something to previous password so it'll be different
        PasswordUpdateParam updateParam = generatePasswordUpdateParam(email, newPassword, previousPassword + "1");

        given(adminRepository.findByEmail(email)).willReturn(Optional.of(admin));

        // when ----------------------------------------------
        // then ----------------------------------------------

        assertThatThrownBy(() -> adminService.changePassword(updateParam))
                .isInstanceOf(WrongPreviousPasswordException.class)
                .hasMessage("Previous Password Is Wrong !");
    }

    private Admin generateAdmin(String email, String previousPassword){
        return Admin.builder()
                .firstname("ahmet")
                .lastname("kaya")
                .password(bCryptPasswordEncoder.encode(previousPassword))
                .email(email)
                .userStatus(UserStatus.PENDING)
                .isEnable(false)
                .isNonLocked(true)
                .credit(Credit.builder().balance(BigDecimal.valueOf(0)).build())
                .build();
    }

    private PasswordUpdateParam generatePasswordUpdateParam(String email, String newPassword, String previousPassword){
        return PasswordUpdateParam.builder()
                .previousPassword(previousPassword)
                .newPassword(bCryptPasswordEncoder.encode(newPassword))
                .email(email)
                .build();
    }
}