package com.softuni.forwardingApp.web;

import com.softuni.forwardingApp.models.dto.AgentAddDto;
import com.softuni.forwardingApp.models.dto.AgentUpdateDto;
import com.softuni.forwardingApp.models.dto.CompanyUpdateDto;
import com.softuni.forwardingApp.models.view.AgentViewModel;
import com.softuni.forwardingApp.service.AgentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("/add-agent")
    public String addAgent() {
        return "auth-add-agent";
    }

    @PostMapping("/add-agent")
    public String confirmAddAgent(@Valid AgentAddDto agentAddDto,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("agentAddDto", agentAddDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.agentAddDto",
                    bindingResult);

            return "redirect:add-agent";
        }

        agentService.addAgent(agentAddDto);

        return "redirect:/";
    }

    @GetMapping("/all-agent")
    public String viewAgents(Model model) {

        List<AgentViewModel> allAgents = agentService.
                findAllAgents();
        model.addAttribute("allAgents", allAgents);

        return "view-all-agents";
    }

    @GetMapping("/edit/{id}")
    public String editAgent(@PathVariable("id") Long id, Model model) {
        AgentUpdateDto agent = agentService.getById(id);
        model.addAttribute("agent", agent);

        return "edit-agent";
    }

    @GetMapping("/save")
    public String saveAgent() {
        return "edit-agent";
    }

    @PostMapping("/save")
    public String confirmSaveAgent(@Valid AgentUpdateDto agent,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("agent", agent);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.agent",
                    bindingResult);

            return "redirect:save";
        }

        agentService.save(agent);

        return "redirect:all-agent";
    }






    @ModelAttribute
    public AgentAddDto agentAddDto() {
        return new AgentAddDto();
    }

}
