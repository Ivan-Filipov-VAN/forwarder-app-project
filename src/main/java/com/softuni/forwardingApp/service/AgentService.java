package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.dto.AgentAddDto;
import com.softuni.forwardingApp.models.dto.AgentUpdateDto;
import com.softuni.forwardingApp.models.entity.AgentEntity;
import com.softuni.forwardingApp.models.view.AgentViewIdName;
import com.softuni.forwardingApp.models.view.AgentViewModel;
import com.softuni.forwardingApp.repositories.AgentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentService {

    private final AgentRepository agentRepository;
    private final ModelMapper modelMapper;

    public AgentService(AgentRepository agentRepository, ModelMapper modelMapper) {
        this.agentRepository = agentRepository;
        this.modelMapper = modelMapper;
    }

    public void initAgent() {
        if (agentRepository.count() > 0) {
            return;
        }
        AgentEntity firstAgent = new AgentEntity()
                .setName("Taiwan Fast")
                .setCountry("Taiwan");

        AgentEntity secondAgent = new AgentEntity()
                .setName("China Pro")
                .setCountry("China");

        AgentEntity thirdAgent = new AgentEntity()
                .setName("Korea Freight")
                .setCountry("Korea");

        agentRepository.save(firstAgent);
        agentRepository.save(secondAgent);
        agentRepository.save(thirdAgent);

        agentRepository.save(new AgentEntity().setName("Fast Ex").setCountry("Philippines"));
        agentRepository.save(new AgentEntity().setName("Scan Pro").setCountry("Germany"));
        agentRepository.save(new AgentEntity().setName("Italy Fast").setCountry("Italy"));
    }


    public AgentEntity addAgent(AgentAddDto agentAddDto) {
        AgentEntity agent = modelMapper.map(agentAddDto, AgentEntity.class);
        agentRepository.save(agent);
        return agent;
    }

    public List<AgentViewModel> findAllAgents() {
        return agentRepository.findAll()
                .stream()
                .map(a -> modelMapper.map(a, AgentViewModel.class))
                .collect(Collectors.toList());
    }

    public AgentUpdateDto getById(Long id) {
        return modelMapper.map(agentRepository.findById(id).orElse(null), AgentUpdateDto.class);
    }

    public AgentEntity save(AgentUpdateDto agent) {
        AgentEntity agentEntity = modelMapper.map(agent, AgentEntity.class);
        agentRepository.save(agentEntity);
        return agentEntity;
    }

    public List<AgentViewIdName> findAllAgentsByIdName() {
        return agentRepository.findAllAgentsByIdName();
    }

    public AgentEntity findById(Long id) {
        return agentRepository.findById(id).orElse(null);
    }




}
