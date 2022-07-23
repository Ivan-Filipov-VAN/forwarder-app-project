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

    public void addAgent(AgentAddDto agentAddDto) {
        agentRepository.save(modelMapper.map(agentAddDto, AgentEntity.class));
    }

    public List<AgentViewModel> findAllAgents() {
        return agentRepository.findAll()
                .stream()
                .map(a -> modelMapper.map(a, AgentViewModel.class))
                .collect(Collectors.toList());
    }

    public AgentUpdateDto getById(Long id) {
        return modelMapper.map(agentRepository.findById(id), AgentUpdateDto.class);
    }

    public void save(AgentUpdateDto agent) {
        agentRepository.save(modelMapper.map(agent, AgentEntity.class));
    }

    public List<AgentViewIdName> findAllAgentsByIdName() {
        return agentRepository.findAllAgentsByIdName();
    }

    public AgentEntity findById(Long id) {
        return agentRepository.findById(id).orElse(null);
    }
}
