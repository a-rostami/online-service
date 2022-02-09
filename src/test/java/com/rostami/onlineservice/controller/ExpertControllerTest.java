package com.rostami.onlineservice.controller;

import com.rostami.onlineservice.service.ExpertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;



@ActiveProfiles("test")
@WebMvcTest(controllers = {ExpertController.class})
@Import(ExpertController.class)
public class ExpertControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExpertService expertService;

    @Test
    public void test_add_subServ_isOk() {}
}
