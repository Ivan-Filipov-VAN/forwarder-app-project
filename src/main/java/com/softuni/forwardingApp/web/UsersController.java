package com.softuni.forwardingApp.web;

import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.models.entity.UserRoleEntity;
import com.softuni.forwardingApp.models.view.UserViewModel;
import com.softuni.forwardingApp.models.view.UserViewModelToSetRoles;
import com.softuni.forwardingApp.service.CompanyService;
import com.softuni.forwardingApp.service.RoleService;
import com.softuni.forwardingApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final CompanyService companyService;
    private final RoleService roleService;

    public UsersController(UserService userService, CompanyService companyService, RoleService roleService) {
        this.userService = userService;
        this.companyService = companyService;
        this.roleService = roleService;
    }

    @GetMapping("/new-users")
    private String newUser(Model model) {

        List<UserEntity> newUsers = userService
                .findAllNewUsers();
        model.addAttribute("newUsers", newUsers);

        return "view-new-users";
    }

    @GetMapping("editAddRoleCompany/{id}")
    public String editUserAddRolesAndCompany(@PathVariable("id") Long id, Model model) {
        UserViewModelToSetRoles user = userService.findUserViewById(id);
        List<CompanyEntity> companies = companyService.findAll();
        List<UserRoleEntity> roles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("companies", companies);
        model.addAttribute("roles", roles);

        return "edit-user-company-roles";
    }

    @PostMapping("/editAddRoleCompany")
    public String confirmAddUserRolesCompany(UserViewModelToSetRoles user) {


//        UserViewModelToSetRoles userViewModelToSetRoles = user;

        userService.setUserRoleAndCompany(user);

        return "redirect:/";
    }

    @GetMapping("/all-users")
    public String viewAllUsers(Model model) {
        List<UserViewModel> allUsers = userService
                .findAllUserViewModels();

        model.addAttribute("allUsers", allUsers);

        return "view-all-users";
    }


}
