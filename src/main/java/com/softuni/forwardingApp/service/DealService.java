package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.dto.DealAddDto;
import com.softuni.forwardingApp.models.dto.DealUpdateDto;
import com.softuni.forwardingApp.models.entity.DealEntity;
import com.softuni.forwardingApp.models.mapper.DealMapper;
import com.softuni.forwardingApp.models.view.DealViewModel;
import com.softuni.forwardingApp.repositories.DealRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        if (dealAddDto.getIdAgent() != null) {
            dealEntity.setAgent(agentService.findById(dealAddDto.getIdAgent()));
        }
        dealEntity.setCompany(companyService.findById(dealAddDto.getIdCompany()));
        dealEntity.setEmployee(userService.findById(dealAddDto.getIdEmployee()));

        dealRepository.save(dealEntity);
    }

//    public List<DealViewModel> findAllDealsViewModel() {
//
//        List<DealEntity> dealEntities = dealRepository.findAll();
//        List<DealViewModel> dealViewModels = dealEntities
//                .stream()
//                .map(d -> {
//                    DealViewModel dealViewModel = dealMapper.dealEntityToDealViewModel(d);
//                        dealViewModel.setCompany(d.getCompany().getName());
//                        dealViewModel.setEmployee(d.getEmployee().getEmail());
//                        if (d.getAgent() != null ) {
//                            dealViewModel.setAgent(d.getAgent().getName());
//                        } else {
//                            dealViewModel.setAgent(null);
//                        }
//                        return dealViewModel;
//                })
//                .collect(Collectors.toList());
//
//        return dealViewModels;
////        return dealRepository.findAllDealsViewModel();
//    }

    public Page<DealViewModel> findAllDealsViewModel(Pageable pageable) {

        return dealRepository.findAll(pageable)
                .map(d -> {
                    DealViewModel dealViewModel = dealMapper.dealEntityToDealViewModel(d);
                    dealViewModel.setCompany(d.getCompany().getName());
                    dealViewModel.setEmployee(d.getEmployee().getEmail());
                    if (d.getAgent() != null) {
                        dealViewModel.setAgent(d.getAgent().getName());
                    } else {
                        dealViewModel.setAgent(null);
                    }
                    return dealViewModel;
                });

    }

    public DealUpdateDto findById(Long id) {
        return dealMapper.dealEntityToDealUpdateDto(dealRepository.findById(id).orElse(null));
    }

    public void updateDeal(DealUpdateDto dealUpdateDto) {
        dealRepository.save(dealMapper.dealEntityToDealUpdateDto(dealUpdateDto));

    }

//    public List<DealViewModel> findAllDealsViewModelByCompanyID(Long id) {
//        return dealRepository.findAllDealsViewModelByCompanyID(id);
//    }

    public List<DealViewModel> findAllDealsViewModelByCompanyID(Long id) {
        List<DealEntity> dealEntities = dealRepository.findByEmployeeId(id);
        return dealEntities
                .stream()
                .map(d -> {
                    DealViewModel dealViewModel = dealMapper.dealEntityToDealViewModel(d);
                    dealViewModel.setCompany(d.getCompany().getName());
//                    dealViewModel.setEmployee(d.getEmployee().getCompName());
//                    if (d.getAgent() != null ) {
//                        dealViewModel.setAgent(d.getAgent().getName());
//                    } else {
//                        dealViewModel.setAgent(null);
//                    }
                    return dealViewModel;
                })
                .collect(Collectors.toList());
    }
}
