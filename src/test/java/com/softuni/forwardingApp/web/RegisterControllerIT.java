package com.softuni.forwardingApp.web;

import com.softuni.forwardingApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService mockEmailService;

    @Test
    void testRegistrationPageShow() throws Exception {
        mockMvc.perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth-register"));
    }

    @Test
    void testUserRegistration() throws Exception {
        mockMvc.perform(post("/users/register")
                .param("email", "dfgan@pan.com")
                .param("phoneNumber", "11111111")
                .param("firstName", "san")
                .param("lastName", "sano")
                .param("password", "4321")
                .param("confirmPassword", "4321")
                .param("compName", "micro")
                .param("compVAT", "111111")
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testUserRegistrationFailed() throws Exception {
        mockMvc.perform(post("/users/register")
                        .param("email", "dfganpan.com")
                        .param("phoneNumber", "1")
                        .param("firstName", "s")
                        .param("lastName", "so")
                        .param("password", "43")
                        .param("confirmPassword", "21")
                        .param("compName", "")
                        .param("compVAT", "")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("register"));
    }
}
