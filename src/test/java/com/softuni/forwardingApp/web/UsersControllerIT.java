package com.softuni.forwardingApp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testAddAgentForbidden() throws Exception {
        mockMvc.perform(get("/users/new-users"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddAgent() throws Exception {
        mockMvc.perform(get("/users/new-users"))
                .andExpect(model().attributeExists("newUsers"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-new-users"));
    }

    @Test
    void testAddAgentNotAuthenticated() throws Exception {
        mockMvc.perform(get("/users/new-users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testEditUserAddRolesAndCompany() throws Exception {
        mockMvc.perform(get("/users/editAddRoleCompany/1"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("companies"))
                .andExpect(model().attributeExists("roles"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-user-company-roles"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testEditUserAddRolesAndCompanyForbidden() throws Exception {
        mockMvc.perform(get("/users/editAddRoleCompany/1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testEditUserAddRolesAndCompanyNotAuthenticated() throws Exception {
        mockMvc.perform(get("/users/editAddRoleCompany/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testConfirmAddUserRolesCompany() throws Exception {
        mockMvc.perform(post("/users/editAddRoleCompany")
                        .param("id", "1")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(roles = {"USER", "EMPLOYEE", "CUSTOMER"})
    void testConfirmAddUserRolesCompanyForbidden() throws Exception {
        mockMvc.perform(post("/users/editAddRoleCompany")
                        .param("id", "1")
                        .with(csrf())
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testConfirmAddUserRolesCompanyNotAuthenticated() throws Exception {
        mockMvc.perform(post("/users/editAddRoleCompany")
                        .param("id", "1")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testViewAllUsers() throws Exception {
        mockMvc.perform(get("/users/all-users"))
                .andExpect(model().attributeExists("allUsers"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-all-users"));
    }

    @Test
    @WithMockUser(roles = {"USER", "CUSTOMER"})
    void testViewAllUsersForbidden() throws Exception {
        mockMvc.perform(get("/users/all-users"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testViewAllUsersNotAuthenticated() throws Exception {
        mockMvc.perform(get("/users/all-users"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }




}
