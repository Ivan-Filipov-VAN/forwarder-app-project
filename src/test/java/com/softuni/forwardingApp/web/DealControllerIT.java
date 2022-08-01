package com.softuni.forwardingApp.web;

import com.softuni.forwardingApp.models.dto.DealUpdateDto;
import com.softuni.forwardingApp.models.entity.AgentEntity;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.models.enums.AirTypeEnum;
import com.softuni.forwardingApp.models.enums.ShipmentStatusEnum;
import com.softuni.forwardingApp.models.user.CurrentUserDetails;
import com.softuni.forwardingApp.service.DealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class DealControllerIT {

    @Autowired
    private MockMvc mockMvc;

//    @Mock
//    private CurrentUserDetails mockUserDetails;

    @MockBean
    private DealService mockDealService;

//    @BeforeEach
//    void setUp() {
//    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testAddDeal() throws Exception {
        mockMvc.perform(get("/deals/add-deal"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("dealAddDto"))
                .andExpect(model().attributeExists("companies"))
                .andExpect(model().attributeExists("agents"))
                .andExpect(view().name("auth-add-deal"));
    }

    @Test
    void testAddDealNotAuthenticated() throws Exception {
        mockMvc.perform(get("/deals/add-deal"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

//    @Test
//    @WithMockUser(roles = "EMPLOYEE")
//    void testConfirmAddDeal() throws Exception {
//
////        when(mockUserDetails.getId()).thenReturn(1L);
//
//        mockMvc.perform(post("/deals/add-deal")
//                        .param("type", "IMP")
//                        .param("mawb", "ryrty")
//                        .param("hawb", "rtyrty")
//                        .param("pieces", "5")
//                        .param("actualWeight", "345")
//                        .param("chargeableWeight", "345")
//                        .param("idCompany", "1")
//                        .param("idAgent", "1")
//                        .param("country", "yt")
//                        .param("airport", "tre")
//                        .with(csrf())
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/"));
//    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testViewAllDeals() throws Exception {
        mockMvc.perform(get("/deals/all-deals"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-all-deals"));
    }

    @Test
    void testViewAllDealsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/deals/all-deals"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testViewAllDealsInTransit() throws Exception {
        mockMvc.perform(get("/deals/all-deals-in-transit"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-all-deals-in-transit"));
    }

    @Test
    void testViewAllDealsInTransitNotAuthenticated() throws Exception {
        mockMvc.perform(get("/deals/all-deals-in-transit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmChangeStatusInTransit() throws Exception {
        mockMvc.perform(get("/deals/change-status-in-transit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-all-deals-in-transit"));

    }

    @Test
    void testConfirmChangeStatusInTransitNotAuthenticated() throws Exception {
        mockMvc.perform(get("/deals/change-status-in-transit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));

    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmChangeStatus() throws Exception {
        mockMvc.perform(get("/deals/change-status/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("view-all-deals"));

    }

    @Test
    void testConfirmChangeStatusNotAuthenticated() throws Exception {
        mockMvc.perform(get("/deals/change-status/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));

    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmEditDeal() throws Exception {
        mockMvc.perform(get("/deals/edit/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("companies"))
                .andExpect(model().attributeExists("agents"))
                .andExpect(view().name("edit-deals"));
    }

    @Test
    void testConfirmEditDealNotAuthenticated() throws Exception {
        mockMvc.perform(get("/deals/edit/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testUpdateDeal() throws Exception {
        mockMvc.perform(get("/deals/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("companies"))
                .andExpect(model().attributeExists("agents"))
                .andExpect(view().name("edit-deals"));
    }

    @Test
    void testUpdateDealNotAuthenticated() throws Exception {
        mockMvc.perform(get("/deals/update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmUpdateDeal() throws Exception {
        mockMvc.perform(post("/deals/update")
                        .param("type", AirTypeEnum.IMP.name())
                        .param("mawb", "ryrty")
                        .param("hawb", "rtyrty")
                        .param("pieces", "5")
                        .param("actualWeight", "345")
                        .param("chargeableWeight", "345")
                        .param("company", "1")
                        .param("agent", "1")
                        .param("country", "yt")
                        .param("airport", "tre")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("all-deals"));
    }

    @Test
    @WithMockUser(roles = "EMPLOYEE")
    void testConfirmUpdateDealWrongType() throws Exception {
        mockMvc.perform(post("/deals/update")
                        .param("type", "VUR")
                        .param("mawb", "ryrty")
                        .param("hawb", "rtyrty")
                        .param("pieces", "5")
                        .param("actualWeight", "345")
                        .param("chargeableWeight", "345")
                        .param("company", "1")
                        .param("agent", "1")
                        .param("country", "yt")
                        .param("airport", "tre")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("update"));
    }

    @Test
    void testConfirmUpdateDealNotAuthenticated() throws Exception {
        mockMvc.perform(post("/deals/update")
                        .param("type", AirTypeEnum.IMP.name())
                        .param("mawb", "ryrty")
                        .param("hawb", "rtyrty")
                        .param("pieces", "5")
                        .param("actualWeight", "345")
                        .param("chargeableWeight", "345")
                        .param("company", "1")
                        .param("agent", "1")
                        .param("country", "yt")
                        .param("airport", "tre")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/users/login"));
    }





}
