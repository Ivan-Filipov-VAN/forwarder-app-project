package com.softuni.forwardingApp.web;

import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.models.user.CurrentUserDetails;
import com.softuni.forwardingApp.models.view.DealViewModel;
import com.softuni.forwardingApp.service.DealService;
import com.softuni.forwardingApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DealCustomerController {

    private final DealService dealService;
    private final UserService userService;

    public DealCustomerController(DealService dealService, UserService userService) {
        this.dealService = dealService;
        this.userService = userService;
    }

    @GetMapping("/customer")
    public ResponseEntity<List<DealViewModel>> customerDealsLast30Days(
            @AuthenticationPrincipal CurrentUserDetails userDetails
            ) {

        UserEntity user = userService.findById(userDetails.getId());
        Long companyId = user.getCompany().getId();


        return ResponseEntity
                .ok(dealService.findAllCustomerDealsLast30Days(companyId));


    }
}
