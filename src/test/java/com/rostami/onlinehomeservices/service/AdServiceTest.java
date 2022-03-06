package com.rostami.onlinehomeservices.service;

import com.rostami.onlinehomeservices.model.Ad;
import com.rostami.onlinehomeservices.repository.AdRepository;
import com.rostami.onlinehomeservices.repository.impl.UpdateRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AdServiceTest {
    @Mock private AdRepository repository;
    @Mock private ExpertService expertService;
    @Mock private UpdateRepositoryImpl<Ad, Long> updateRepository;

    private AdService adService;

    @BeforeEach
    void setUp() {
        adService = new AdService(repository, expertService, updateRepository);
        adService.init();
    }

    @Test
    void test_findAllAdsOfCustomer_isOk() {
    }

    @Test
    void findAdsRelatedToExpertSubServ() {
    }

    @Test
    void chooseExpert() {
    }

    @Test
    void setAdToDone() {
    }
}