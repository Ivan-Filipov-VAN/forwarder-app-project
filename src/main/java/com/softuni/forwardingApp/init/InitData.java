package com.softuni.forwardingApp.init;

import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserRoleEntity;
import com.softuni.forwardingApp.models.enums.UserRoleEnum;
import com.softuni.forwardingApp.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    private final CompanyService companyService;
    private final AgentService agentService;

    private final DealService dealService;

    public InitData(RoleService roleService
            , UserService userService
            , CompanyService companyService
            , AgentService agentService,
                    DealService dealService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;

        this.agentService = agentService;
        this.dealService = dealService;
    }

    @Override
    public void run(String... args) throws Exception {

        roleService.initRoles();

        CompanyEntity company = companyService.initCompany();

        UserRoleEntity admin = roleService.findByUserRole(UserRoleEnum.ADMIN);
        UserRoleEntity customer = roleService.findByUserRole(UserRoleEnum.EMPLOYEE);
        UserRoleEntity client = roleService.findByUserRole(UserRoleEnum.CUSTOMER);

        userService.initAdmin(List.of(admin, customer), company);

        userService.initUsers(List.of(customer, client), company);

        agentService.initAgent();

        dealService.initDeals();



    }
}
