package com.softuni.forwardingApp.web;

import com.softuni.forwardingApp.models.dto.CompanyAddDto;
import com.softuni.forwardingApp.models.dto.CompanyUpdateDto;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.service.CompanyService;
import com.softuni.forwardingApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {


    private final UserService userService;
    private final CompanyService companyService;

    public CompanyController(UserService userService, CompanyService companyService) {
        this.userService = userService;

        this.companyService = companyService;
    }

    @GetMapping("/add-company/{id}")
    public String addCompanyFromNewUser(@PathVariable("id") Long id, Model model) {
        UserEntity user = userService.findById(id);
        CompanyAddDto companyAddDto = new CompanyAddDto()
                .setName(user.getCompName())
                .setVat(user.getCompVAT());
        model.addAttribute("companyAddDto", companyAddDto);

        return "auth-add-company";
    }

    @GetMapping("/add-company")
    public String addCompany(){
        return "auth-add-company";
    }

    @PostMapping("/add-company")
    public String confirmAddCompany(@Valid CompanyAddDto companyAddDto,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("companyAddDto", companyAddDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.companyAddDto",
                    bindingResult);

            return "redirect:add-company";
        }

        companyService.addCompany(companyAddDto);

        return "redirect:/";
    }

    @GetMapping("/all-company")
    public String viewCompanies(Model model) {

        List<CompanyEntity> allCompanies = companyService
                .findAll();
        model.addAttribute("allCompanies", allCompanies);

        return "view-all-company";
    }

    @GetMapping("/edit/{id}")
    public String editCompany(@PathVariable("id") Long id, Model model) {
        CompanyUpdateDto company = companyService.getById(id);
        model.addAttribute("company", company);

        return "edit-company";
    }

    @GetMapping("/save")
    public String confirmUpdateCompany() {
        return "edit-company";
    }


    @PostMapping("/save")
    public String saveCompany(@Valid CompanyUpdateDto company,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("company", company);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.company",
                    bindingResult);

            return "redirect:save";
        }

        companyService.save(company);

        return "redirect:all-company";
    }


    @ModelAttribute
    public CompanyAddDto companyAddDto() {
        return new CompanyAddDto();
    }
}
