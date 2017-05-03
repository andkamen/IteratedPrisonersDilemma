package com.ipdweb.areas.user.controllers;

import com.ipdweb.areas.common.interceptors.ContentValidator;
import com.ipdweb.areas.user.models.bindingModels.RegistrationModel;
import com.ipdweb.areas.user.services.BasicUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BasicUserService userService;

    @MockBean
    private ContentValidator contentValidator;

    @Test
    public void registerUser() throws Exception {
        mockMvc
                .perform(post("/register")
                        .param("username", "testName1234")
                        .param("password", "1234")
                        .param("confirmPassword", "1234")
                )
                //TODO custom validator throws false for name being valid
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"));


        ArgumentCaptor<RegistrationModel> captor = ArgumentCaptor.forClass(RegistrationModel.class);
        verify(userService).register(captor.capture());
        RegistrationModel user = captor.getValue();
        assertEquals("testName1234", user.getUsername());
    }

}