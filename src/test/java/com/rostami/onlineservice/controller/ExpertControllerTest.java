package com.rostami.onlineservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rostami.onlineservice.dto.in.update.MainServUpdateParam;
import com.rostami.onlineservice.dto.in.update.SubServUpdateParam;
import com.rostami.onlineservice.dto.out.CreateUpdateResult;
import com.rostami.onlineservice.service.ExpertService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@WebMvcTest(controllers = {ExpertController.class})
@Import(ExpertController.class)
public class ExpertControllerTest extends AbstractRestControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExpertService expertService;

    @SneakyThrows
    @Test
    public void test_add_subServ_isOk() {
        //mock Service
        CreateUpdateResult result = CreateUpdateResult.builder()
                .success(true)
                .id(1L)
                .build();
        SubServUpdateParam subServParam = SubServUpdateParam.builder()
                .id(1L)
                .name("testSubService")
                .basePrice(BigDecimal.valueOf(15000))
                .mainServUpdateParam(MainServUpdateParam.builder().id(1L).name("HomeAppliance").build())
                .build();

        Mockito.when(expertService.addSubServ(1L, subServParam)).thenReturn(result);

        mvc.perform(post("/experts/addSubServ/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(subServParam))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
