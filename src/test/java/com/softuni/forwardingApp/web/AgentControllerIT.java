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
public class AgentControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testAddAgent() throws Exception {
        mockMvc.perform(get("/agents/add-agent"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth-add-agent"));
    }

    @Test
    void testAddAgentNotAuthenticated() throws Exception {
        mockMvc.perform(get("/agents/add-agent"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmAddAgent() throws Exception {
        mockMvc.perform(post("/agents/add-agent")
                        .param("name", "fifth")
                        .param("vat", "555555")
                        .param("country", "mladost 3")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmAddAgentWrongData() throws Exception {
        mockMvc.perform(post("/agents/add-agent")
                        .param("name", "f")
                        .param("vat", "3")
                        .param("country", "f")
                        .with(csrf())
                )
                .andExpect(flash().attributeExists("agentAddDto"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("add-agent"));
    }

    @Test
    void testConfirmAddAgentNotAuthenticated() throws Exception {
        mockMvc.perform(post("/agents/add-agent")
                        .param("name", "fifth")
                        .param("vat", "555555")
                        .param("country", "mladost 3")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testAllAgent() throws Exception {
        mockMvc.perform(get("/agents/all-agent"))
                .andExpect(model().attributeExists("allAgents"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-all-agents"));
    }

    @Test
    void testAllAgentNotAuthenticated() throws Exception {
        mockMvc.perform(get("/agents/all-agent"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testEditAgent() throws Exception {
        mockMvc.perform(get("/agents/edit/1"))
                .andExpect(model().attributeExists("agent"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-agent"));
    }

    @Test
    void testEditAgentNotAuthenticated() throws Exception {
        mockMvc.perform(get("/agents/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testSaveAgent() throws Exception {
        mockMvc.perform(get("/agents/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-agent"));
    }

    @Test
    void testSaveAgentNotAuthenticated() throws Exception {
        mockMvc.perform(get("/agents/save"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmSaveAgent() throws Exception {
        mockMvc.perform(post("/agents/save")
                        .param("name", "fifth")
                        .param("country", "de")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("all-agent"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmSaveAgentWrongData() throws Exception {
        mockMvc.perform(post("/agents/save")
                        .param("name", "f")
                        .param("vat", "3")
                        .param("country", "f")
                        .with(csrf())
                )
                .andExpect(flash().attributeExists("agent"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("save"));
    }

    @Test
    void testConfirmSaveAgentNotAuthenticated() throws Exception {
        mockMvc.perform(post("/agents/save")
                        .param("name", "fifth")
                        .param("vat", "555555")
                        .param("country", "mladost 3")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }




}
