package com.softuni.forwardingApp.init;

import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserRoleEntity;
import com.softuni.forwardingApp.models.enums.UserRoleEnum;
import com.softuni.forwardingApp.service.CompanyService;
import com.softuni.forwardingApp.service.RoleService;
import com.softuni.forwardingApp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    private final CompanyService companyService;

    public InitData(RoleService roleService, UserService userService, CompanyService companyService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.initRoles();

        CompanyEntity company = companyService.initCompany();

        UserRoleEntity admin = roleService.findByUserRole(UserRoleEnum.ADMIN);
        UserRoleEntity customer = roleService.findByUserRole(UserRoleEnum.EMPLOYEE);

        userService.initAdmin(List.of(admin, customer), company);
    }
}
