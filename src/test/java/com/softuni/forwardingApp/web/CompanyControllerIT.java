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
public class CompanyControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testAddCompany() throws Exception {
        mockMvc.perform(get("/companies/add-company"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("companyAddDto"))
                .andExpect(view().name("auth-add-company"));
    }

    @Test
    void testAddCompanyWithNotAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/companies/add-company"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }


    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testAddCompanyFromNewUser() throws Exception {

        mockMvc.perform(get("/companies/add-company/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("companyAddDto"))
                .andExpect(view().name("auth-add-company"));
    }

    @Test
    void testAddCompanyFromNewUserNotAuthenticated() throws Exception {

        mockMvc.perform(get("/companies/add-company/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmAddCompany() throws Exception {
        mockMvc.perform(post("/companies/add-company")
                .param("name", "fifth")
                .param("vat", "555555")
                .param("address", "mladost 3")
                .param("accountablePerson", "pesho peshistiya")
                .with(csrf())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    @Test
    void testAddCompanyNotAuthenticated() throws Exception {
        mockMvc.perform(post("/companies/add-company")
                        .param("name", "fifth")
                        .param("vat", "555555")
                        .param("address", "mladost 3")
                        .param("accountablePerson", "pesho peshistiya")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));

    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testWrongAddCompany() throws Exception {
        mockMvc.perform(post("/companies/add-company")
                        .param("name", "f")
                        .param("vat", "1")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("add-company"));

    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testViewAllCompany() throws Exception {
        mockMvc.perform(get("/companies/all-company"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("allCompanies"))
                .andExpect(view().name("view-all-company"));

    }

    @Test
    void testViewAllCompanyNotAuthenticated() throws Exception {
        mockMvc.perform(get("/companies/all-company"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));

    }



    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testEditCompanyById() throws Exception {

        mockMvc.perform(get("/companies/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("company"))
                .andExpect(view().name("edit-company"));
    }

    @Test
    void testEditCompanyByIdNotAuthenticated() throws Exception {

        mockMvc.perform(get("/companies/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmEditCompanyById() throws Exception {

        mockMvc.perform(get("/companies/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-company"));
    }

    @Test
    void testConfirmEditCompanyByIdNotAuthenticated() throws Exception {

        mockMvc.perform(get("/companies/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmEditCompany() throws Exception {
        mockMvc.perform(post("/companies/save")
                        .param("name", "fifth")
                        .param("vat", "555555")
                        .param("address", "mladost 3")
                        .param("accountablePerson", "pesho peshistiya")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("all-company"));

    }

    @Test
    void testEditCompanyNotAuthenticated() throws Exception {
        mockMvc.perform(post("/companies/save")
                        .param("name", "fifth")
                        .param("vat", "555555")
                        .param("address", "mladost 3")
                        .param("accountablePerson", "pesho peshistiya")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));

    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testWrongEditCompany() throws Exception {
        mockMvc.perform(post("/companies/save")
                        .param("name", "f")
                        .param("vat", "1")
                        .with(csrf())
                )
                .andExpect(flash().attributeExists("company"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("save"));

    }





}
