package com.softuni.forwardingApp.web;

import com.softuni.forwardingApp.models.dto.DealAddDto;
import com.softuni.forwardingApp.models.dto.DealUpdateDto;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.models.user.CurrentUserDetails;
import com.softuni.forwardingApp.models.view.AgentViewIdName;
import com.softuni.forwardingApp.models.view.DealViewModel;
import com.softuni.forwardingApp.service.AgentService;
import com.softuni.forwardingApp.service.CompanyService;
import com.softuni.forwardingApp.service.DealService;
import com.softuni.forwardingApp.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    private final UserService userService;

    public DealController(CompanyService companyService, AgentService agentService, DealService dealService, UserService userService) {
        this.companyService = companyService;
        this.agentService = agentService;
        this.dealService = dealService;
        this.userService = userService;
    }

    @GetMapping("/add-deal")
    public String addDeal(Model model) {

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
    private String viewAllDeals(Model model,
                                @PageableDefault(
                                        page = 0,
                                        size = 15,
                                        sort = "id",
                                        direction = Sort.Direction.DESC
                                ) Pageable pageable) {

        Page<DealViewModel> allDeals = dealService.
                findAllDealsViewModel(pageable);

        model.addAttribute("allDeals", allDeals);
        return "view-all-deals";
    }

    @GetMapping("all-deals-not-closed")
    private String viewAllDealsNotClosed(Model model,
                                         @PageableDefault(
                                                 page = 0,
                                                 size = 15
                                         ) Pageable pageable ) {

        Page<DealViewModel> allDeals = dealService.
                findAllDealsViewModelNotClosed(pageable);

        model.addAttribute("allDeals", allDeals);
        return "view-all-deals-not-closed";

    }

    @GetMapping("/all-deals-in-transit")
    private String viewAllDealsInTransit(Model model,
                                @PageableDefault(
                                        page = 0,
                                        size = 15
                                ) Pageable pageable) {

        Page<DealViewModel> allDeals = dealService.
                findAllDealsViewModelInTransit(pageable);

        model.addAttribute("allDeals", allDeals);
        return "view-all-deals-in-transit";
    }


    @GetMapping("/change-status-in-transit/{id}")
    public String confirmChangeStatusInTransit(
            @PathVariable("id") Long id,
            Model model,
            @PageableDefault(
                    page = 0,
                    size = 15
            ) Pageable pageable
    ) {
        DealUpdateDto dealUpdateDto = dealService.findById(id);
        dealService.changeStatus(dealUpdateDto);
        dealService.updateDeal(dealUpdateDto);

        Page<DealViewModel> allDeals = dealService.
                findAllDealsViewModelInTransit(pageable);

        model.addAttribute("allDeals", allDeals);
        return "view-all-deals-in-transit";
    }

    @GetMapping("/change-status/{id}")
    public String confirmChangeStatus(
            @PathVariable("id") Long id,
            Model model,
            @PageableDefault(
                    page = 0,
                    size = 15,
                    sort = "id",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
            ) {
        DealUpdateDto dealUpdateDto = dealService.findById(id);
        dealService.changeStatus(dealUpdateDto);
        dealService.updateDeal(dealUpdateDto);

        Page<DealViewModel> allDeals = dealService.
                findAllDealsViewModel(pageable);

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
        model.addAttribute("booleanTrue", true);

        return "edit-deals";
    }

    @GetMapping("/update")
    public String confirmUpdateDeal(Model model) {

        List<CompanyEntity> companies = companyService.findAll();
        List<AgentViewIdName> agents = agentService.findAllAgentsByIdName();

        model.addAttribute("companies", companies);
        model.addAttribute("agents", agents);

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

        @GetMapping("/customer-deals")
        private String viewCustomerDeals(
            Model model,
            @PageableDefault(
                    page = 0,
                    size = 3
            )
            Pageable pageable,
            @AuthenticationPrincipal CurrentUserDetails userDetails) {
        UserEntity user = userService.findById(userDetails.getId());
        if (user.getCompany() == null) {
            return "error-user-company-not-exist";
        }
        Long companyId = user.getCompany().getId();

        Page<DealViewModel> customerDeals = dealService.findAllDealsViewModelByCompanyIDInTransit(pageable, companyId);

        model.addAttribute("customerDeals", customerDeals);

        return "view-customer-deals";
    }

    @GetMapping("/customer-all-deals")
    private String viewAllCustomerDeals(
            Model model,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable,
            @AuthenticationPrincipal CurrentUserDetails userDetails) {
        UserEntity user = userService.findById(userDetails.getId());
        if (user.getCompany() == null) {
            return "error-user-company-not-exist";
        }
        Long companyId = user.getCompany().getId();

        Page<DealViewModel> customerDeals = dealService.findAllDealsViewModelByCompanyID(pageable, companyId);

        model.addAttribute("customerDeals", customerDeals);

        return "view-customer-all-deals";
    }

    @ModelAttribute
    public DealAddDto dealAddDto() {
        return new DealAddDto();
    }
}
