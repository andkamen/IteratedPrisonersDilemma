package com.ipdweb.areas.strategy.controllers;

import com.ipdweb.areas.common.interceptors.ContentValidator;
import com.ipdweb.areas.strategy.services.StrategyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StrategyController.class)
@ActiveProfiles("test")
public class StrategyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StrategyService strategyService;

    @MockBean
    private ContentValidator contentValidator;

    @Test
    public void getStrategiesPage_ShouldReturnPage() throws Exception {
        this.mvc
                .perform(get("/strategies"))
                .andExpect(status().isOk());
    }

}