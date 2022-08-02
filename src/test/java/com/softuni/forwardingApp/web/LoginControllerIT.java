package com.softuni.forwardingApp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth-login"));
    }

//    @Test
//    void testErrorLogin() throws Exception {
//        mockMvc.perform(post("/users/login-error")
//                        .with(csrf())
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("http://localhost/users/login"));
//    }

//    @Test
//    void testErrorLogin() throws Exception {
//        mockMvc.perform(post("/users/login-error")
//                        .param("username", UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
//                        .with(csrf())
//                )
////                .andExpect(model().attributeExists("invalid_input"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("login"));
//    }
}
