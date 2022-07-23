package com.softuni.forwardingApp.web;

import com.softuni.forwardingApp.models.dto.CompanyUpdateDto;
import com.softuni.forwardingApp.models.dto.DealAddDto;
import com.softuni.forwardingApp.models.dto.DealUpdateDto;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.user.CurrentUserDetails;
import com.softuni.forwardingApp.models.view.AgentViewIdName;
import com.softuni.forwardingApp.models.view.DealViewModel;
import com.softuni.forwardingApp.service.AgentService;
import com.softuni.forwardingApp.service.CompanyService;
import com.softuni.forwardingApp.service.DealService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/deals")
public class DealController {

    private final CompanyService companyService;
    private final AgentService agentService;
    private final DealService dealService;

    public DealController(CompanyService companyService, AgentService agentService, DealService dealService) {
        this.companyService = companyService;
        this.agentService = agentService;
        this.dealService = dealService;
    }

    @GetMapping("/add-deal")
    public String addDeal(Model model){

        List<CompanyEntity> companies = companyService.findAll();
        List<AgentViewIdName> agents = agentService.findAllAgentsByIdName();

        model.addAttribute("companies", companies);
        model.addAttribute("agents", agents);

        return "auth-add-deal";
    }

    @PostMapping("/add-deal")
    public String confirmAddDeal(@Valid DealAddDto dealAddDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal CurrentUserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("dealAddDto", dealAddDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.dealAddDto",
                    bindingResult);

            return "redirect:add-deal";
        }

        dealAddDto.setIdEmployee(userDetails.getId());
        dealService.addDeal(dealAddDto);

        return "redirect:/";

    }

    @GetMapping("/all-deals")
    private String viewAllDeals(Model model) {

        List<DealViewModel> allDeals = dealService.
                findAllDealsViewModel();

        model.addAttribute("allDeals", allDeals);
        return "view-all-deals";
    }

    @GetMapping("/edit/{id}")
    public String editDeal(@PathVariable("id") Long id, Model model) {
        DealUpdateDto dealUpdateDto = dealService.findById(id);
        model.addAttribute("dealUpdateDto", dealUpdateDto);

        List<CompanyEntity> companies = companyService.findAll();
        List<AgentViewIdName> agents = agentService.findAllAgentsByIdName();

        model.addAttribute("companies", companies);
        model.addAttribute("agents", agents);

        return "edit-deals";
    }

    @GetMapping("/update")
    public String confirmUpdateDeal() {
        return "edit-deals";
    }

    @PostMapping("/update")
    public String updateDeal(@Valid DealUpdateDto dealUpdateDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("dealUpdateDto", dealUpdateDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.dealUpdateDto",
                    bindingResult);

            return "redirect:update";
        }

        dealService.updateDeal(dealUpdateDto);

        return "redirect:all-deals";
    }

    @ModelAttribute
    public DealAddDto dealAddDto() {
        return new DealAddDto();
    }
}
