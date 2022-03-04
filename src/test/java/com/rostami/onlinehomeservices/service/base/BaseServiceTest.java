package com.rostami.onlinehomeservices.service.base;

import com.rostami.onlinehomeservices.dto.in.create.MainServCreateParam;
import com.rostami.onlinehomeservices.dto.in.update.MainServUpdateParam;
import com.rostami.onlinehomeservices.dto.out.CreateUpdateResult;
import com.rostami.onlinehomeservices.dto.out.single.MainServFindResult;
import com.rostami.onlinehomeservices.exception.EntityLoadException;
import com.rostami.onlinehomeservices.model.MainServ;
import com.rostami.onlinehomeservices.repository.MainServRepository;
import com.rostami.onlinehomeservices.repository.impl.UpdateRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rostami.onlinehomeservices.exception.messages.ExceptionMessages.ENTITY_ID_LOAD_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/*
 * This class is a base class so all the classes that extend
 * this class have its methods. in this test I'm using mainServ
 * which is one of its subclasses to test BaseService */

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BaseServiceTest {
    @Mock
    private MainServRepository mainServRepository;
    @Mock
    private UpdateRepositoryImpl<MainServ, Long> updateRepository;
    private BaseService<MainServ, Long, MainServFindResult> baseService;


    @BeforeEach
    void setUp() {
        baseService = new BaseService<>();
        baseService.setBaseOutDto(new MainServFindResult());
        baseService.setRepository(mainServRepository);
        baseService.setUpdateRepositoryImpl(updateRepository);
    }

    @Test
    void test_save_isOk() {
        // given -------------------------------------------------------------------
        MainServCreateParam createParam = MainServCreateParam.builder()
                .name("Sample Main Service")
                .build();
        MainServ mainServ = createParam.convertToDomain();
        mainServ.setId(1L);
        given(mainServRepository.save(createParam.convertToDomain())).willReturn(mainServ);

        // when  -------------------------------------------------------------------
        CreateUpdateResult result = baseService.save(createParam);

        // then  -------------------------------------------------------------------
        ArgumentCaptor<MainServ> argumentCaptor = ArgumentCaptor.forClass(MainServ.class);
        verify(mainServRepository).save(argumentCaptor.capture());

        MainServ capturedValue = argumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(createParam.convertToDomain());
        assertThat(result.getId()).isEqualTo(mainServ.getId());
    }

    @Test
    void test_update_isOk() {
        // given -------------------------------------------------------------------------------------------------
        MainServUpdateParam updateParam = MainServUpdateParam.builder()
                .name("sample main service")
                .id(1L)
                .build();

        CreateUpdateResult createUpdateResult = CreateUpdateResult.builder()
                .id(updateParam.getId())
                .success(true)
                .build();
        given(updateRepository.update(updateParam, updateParam.getId())).willReturn(createUpdateResult);


        // when  -----------------------------------------------------------------------------------------------
        CreateUpdateResult result = baseService.update(updateParam, updateParam.getId());


        // then  -----------------------------------------------------------------------------------------------
        ArgumentCaptor<MainServUpdateParam> paramArgumentCaptor = ArgumentCaptor.forClass(MainServUpdateParam.class);
        ArgumentCaptor<Long> idArgumentCapture = ArgumentCaptor.forClass(Long.class);
        verify(updateRepository).update(paramArgumentCaptor.capture(), idArgumentCapture.capture());
        MainServUpdateParam paramArgumentCaptorValue = paramArgumentCaptor.getValue();
        Long idArgumentCaptureValue = idArgumentCapture.getValue();


        assertThat(paramArgumentCaptorValue).isEqualTo(updateParam);
        assertThat(idArgumentCaptureValue).isEqualTo(updateParam.getId());
        assertThat(result.getId()).isEqualTo(createUpdateResult.getId());
    }

    @Test
    void test_findById_isOk() {
        // given -------------------------------------------------------------------------------------------------
        Long id = 1L;
        MainServ mainServ = MainServ.builder()
                .id(id)
                .name("Sample MainService")
                .build();
        given(mainServRepository.findById(id)).willReturn(Optional.of(mainServ));

        // when  -----------------------------------------------------------------------------------------------
        MainServFindResult byId = (MainServFindResult) baseService.findById(id);

        // then  -----------------------------------------------------------------------------------------------
        assertThat(byId.getId()).isEqualTo(mainServ.getId());
    }

    @Test
    void test_findById_willThrow_ifNotFound() {
        // given -------------------------------------------------------------------------------------------------
        Long id = 1L;
        given(mainServRepository.findById(id)).willReturn(Optional.empty());


        // when  -----------------------------------------------------------------------------------------------
        // then  -----------------------------------------------------------------------------------------------
        assertThatThrownBy(() -> baseService.findById(id))
                .isInstanceOf(EntityLoadException.class)
                .hasMessage(ENTITY_ID_LOAD_MESSAGE);
    }

    @Test
    void test_findAll_isOk() {
        // given -------------------------------------------------------------------------------------------------
        List<MainServ> results = List.of(MainServ.builder()
                        .id(1L)
                        .name("sample name")
                        .build(),
                MainServ.builder()
                        .id(2L)
                        .name("sample name")
                        .build());

        given(mainServRepository.findAll()).willReturn(results);

        Set<MainServFindResult> mainServFindResults = results.stream()
                .map((entity) -> new MainServFindResult().convertToDto(entity)).collect(Collectors.toSet());

        // when  -----------------------------------------------------------------------------------------------
        Set<MainServFindResult> findResults = baseService.findAll()
                .stream().map(dto -> (MainServFindResult) dto).collect(Collectors.toSet());

        // then  -----------------------------------------------------------------------------------------------
        for (MainServFindResult findResult : findResults) {
            assertThat(mainServFindResults.contains(findResult)).isTrue();
        }
    }

    @Test
    void test_deleteById_isOk() {
        // given -------------------------------------------------------------------------------------------------
        Long id = 1L;

        // when  -----------------------------------------------------------------------------------------------
        baseService.delete(id);

        // then  -----------------------------------------------------------------------------------------------
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(mainServRepository).deleteById(argumentCaptor.capture());
        Long captorValue = argumentCaptor.getValue();

        assertThat(captorValue).isEqualTo(id);
    }
}