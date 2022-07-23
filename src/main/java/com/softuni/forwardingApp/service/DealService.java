package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.dto.DealAddDto;
import com.softuni.forwardingApp.models.dto.DealUpdateDto;
import com.softuni.forwardingApp.models.entity.DealEntity;
import com.softuni.forwardingApp.models.mapper.DealMapper;
import com.softuni.forwardingApp.models.view.DealViewModel;
import com.softuni.forwardingApp.repositories.DealRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealService {

    private final DealRepository dealRepository;

    private final AgentService agentService;
    private final CompanyService companyService;
    private final UserService userService;
    private final DealMapper dealMapper;

    public DealService(DealRepository dealRepository,
                       AgentService agentService,
                       CompanyService companyService,
                       UserService userService, DealMapper dealMapper) {
        this.dealRepository = dealRepository;
        this.agentService = agentService;
        this.companyService = companyService;
        this.userService = userService;
        this.dealMapper = dealMapper;
    }

    public void addDeal(DealAddDto dealAddDto) {
        DealEntity dealEntity = dealMapper.dealAddDtoToDealEntity(dealAddDto);

        dealEntity.setAgent(agentService.findById(dealAddDto.getIdAgent()));
        dealEntity.setCompany(companyService.findById(dealAddDto.getIdCompany()));
        dealEntity.setEmployee(userService.findById(dealAddDto.getIdEmployee()));

        dealRepository.save(dealEntity);
    }

    public List<DealViewModel> findAllDealsViewModel() {
        return dealRepository.findAllDealsViewModel();
    }

    public DealUpdateDto findById(Long id) {
        return dealMapper.dealEntityToDealUpdateDto(dealRepository.findById(id).orElse(null));
    }

    public void updateDeal(DealUpdateDto dealUpdateDto) {
        dealRepository.save(dealMapper.dealEntityToDealUpdateDto(dealUpdateDto));

    }
}
