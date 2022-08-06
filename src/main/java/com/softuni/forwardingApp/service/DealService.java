package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.dto.DealAddDto;
import com.softuni.forwardingApp.models.dto.DealUpdateDto;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.DealEntity;
import com.softuni.forwardingApp.models.enums.AirTypeEnum;
import com.softuni.forwardingApp.models.enums.ShipmentStatusEnum;
import com.softuni.forwardingApp.models.mapper.DealMapper;
import com.softuni.forwardingApp.models.view.DealViewModel;
import com.softuni.forwardingApp.models.view.StatisticViewModel;
import com.softuni.forwardingApp.repositories.DealRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DealService {

    private static final ShipmentStatusEnum DELIVERED_STATUS = ShipmentStatusEnum.DONE;

    private final DealRepository dealRepository;

    private final AgentService agentService;
    private final CompanyService companyService;
    private final UserService userService;
    private final DealMapper dealMapper;
    private final EmailService emailService;

    public DealService(DealRepository dealRepository,
                       AgentService agentService,
                       CompanyService companyService,
                       UserService userService,
                       DealMapper dealMapper,
                       EmailService emailService) {
        this.dealRepository = dealRepository;
        this.agentService = agentService;
        this.companyService = companyService;
        this.userService = userService;
        this.dealMapper = dealMapper;
        this.emailService = emailService;
    }

    public DealEntity addDeal(DealAddDto dealAddDto) {
        DealEntity dealEntity = dealMapper.dealAddDtoToDealEntity(dealAddDto);
        if (dealAddDto.getIdAgent() != null) {
            dealEntity.setAgent(agentService.findById(dealAddDto.getIdAgent()));
        }
        dealEntity.setArchive(false);
        dealEntity.setCompany(companyService.findById(dealAddDto.getIdCompany()));
        dealEntity.setEmployee(userService.findById(dealAddDto.getIdEmployee()));
        dealEntity.setStatus(ShipmentStatusEnum.SHIPPER_CONTACTED);

        dealRepository.save(dealEntity);
        return dealEntity;
    }

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

    public Page<DealViewModel> findAllDealsViewModelInTransit(Pageable pageable) {

        return dealRepository.findAllDealsInTransit(pageable, DELIVERED_STATUS)
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
        if (dealUpdateDto.getArchive() == null) {
            dealUpdateDto.setArchive(false);
        }
        DealEntity dealEntity = dealMapper.dealUpdateDtoToDealEntity(dealUpdateDto);
        dealRepository.save(dealEntity);

    }

    public Page<DealViewModel> findAllDealsViewModelByCompanyID(Pageable pageable, Long id) {
        return dealRepository.findByEmployeeId(pageable, id)
                    .map(d -> {
                    DealViewModel dealViewModel = dealMapper.dealEntityToDealViewModel(d);
                    dealViewModel.setCompany(d.getCompany().getName());
                    return dealViewModel;
                });
    }

    public Page<DealViewModel> findAllDealsViewModelByCompanyIDInTransit(Pageable pageable, Long id) {
        return dealRepository.findByEmployeeIdInTransit(pageable, id, DELIVERED_STATUS)
                .map(d -> {
                    DealViewModel dealViewModel = dealMapper.dealEntityToDealViewModel(d);
                    dealViewModel.setCompany(d.getCompany().getName());
                    return dealViewModel;
                });
    }

    public Page<DealViewModel> findAllDealsViewModelNotClosed(Pageable pageable) {
        return dealRepository.findByArchiveFalse(pageable)
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


    public void changeStatus(DealUpdateDto dealUpdateDto) {
        switch (dealUpdateDto.getStatus()) {
            case SHIPPER_CONTACTED -> dealUpdateDto.setStatus(ShipmentStatusEnum.PICK_UP);
            case PICK_UP -> dealUpdateDto.setStatus(ShipmentStatusEnum.DEPARTED);
            case DEPARTED -> dealUpdateDto.setStatus(ShipmentStatusEnum.IN_TRANSIT);
            case IN_TRANSIT -> dealUpdateDto.setStatus(ShipmentStatusEnum.ARRIVED);
            case ARRIVED -> dealUpdateDto.setStatus(ShipmentStatusEnum.CLEARANCE);
            case CLEARANCE -> dealUpdateDto.setStatus(ShipmentStatusEnum.OUT_FOR_DELIVERY);
            case OUT_FOR_DELIVERY -> dealUpdateDto.setStatus(ShipmentStatusEnum.DELIVERED);
            case DELIVERED -> dealUpdateDto.setStatus(ShipmentStatusEnum.DONE);
            case DONE -> dealUpdateDto.setStatus(ShipmentStatusEnum.DONE);

        }
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void sendStatistic() {
        Long imp = dealRepository.getStatistic(AirTypeEnum.IMP, LocalDate.now().minusDays(1));
        Long exp = dealRepository.getStatistic(AirTypeEnum.EXP, LocalDate.now().minusDays(1));
        StatisticViewModel statistic = new StatisticViewModel(imp, exp);

        emailService.sendStatistic(statistic);
    }


    public void initDeals() {
        if (dealRepository.count() > 0) {
            return;
        }

        CompanyEntity companyEntity = companyService.findById(2L);

        DealEntity first = new DealEntity()
                .setType(AirTypeEnum.IMP)
                .setMawb("234-4444 5555")
                .setHawb("HAWB345345")
                .setPieces(4)
                .setActualWeight(345.0)
                .setChargeableWeight(345.0)
                .setCompany(companyEntity)
                .setAgent(agentService.findById(3L))
                .setCountry("KR")
                .setAirport("ICN")
                .setDate(LocalDate.now())
                .setStatus(ShipmentStatusEnum.PICK_UP)
                .setEmployee(userService.findById(1L))
                .setArchive(false);

        dealRepository.save(first);

        DealEntity second = new DealEntity()
                .setType(AirTypeEnum.IMP)
                .setMawb("234-4444 5566")
                .setHawb("HAWB345346")
                .setPieces(7)
                .setActualWeight(456.0)
                .setChargeableWeight(555.0)
                .setCompany(companyEntity)
                .setAgent(agentService.findById(4L))
                .setCountry("PH")
                .setAirport("MNL")
                .setDate(LocalDate.now())
                .setStatus(ShipmentStatusEnum.DEPARTED)
                .setEmployee(userService.findById(1L))
                .setArchive(false);

        dealRepository.save(second);

        DealEntity third = new DealEntity()
                .setType(AirTypeEnum.IMP)
                .setMawb("234-4444 5577")
                .setHawb("HAWB345347")
                .setPieces(2)
                .setActualWeight(123.0)
                .setChargeableWeight(123.0)
                .setCompany(companyEntity)
                .setAgent(agentService.findById(4L))
                .setCountry("PH")
                .setAirport("MNL")
                .setDate(LocalDate.now())
                .setStatus(ShipmentStatusEnum.ARRIVED)
                .setEmployee(userService.findById(1L))
                .setArchive(false);

        dealRepository.save(third);

        DealEntity fourth = new DealEntity()
                .setType(AirTypeEnum.IMP)
                .setMawb("234-4444 5588")
                .setHawb("HAWB345348")
                .setPieces(8)
                .setActualWeight(666.0)
                .setChargeableWeight(678.0)
                .setCompany(companyEntity)
                .setAgent(agentService.findById(2L))
                .setCountry("CN")
                .setAirport("BJS")
                .setDate(LocalDate.now())
                .setStatus(ShipmentStatusEnum.IN_TRANSIT)
                .setEmployee(userService.findById(1L))
                .setArchive(false);

        dealRepository.save(fourth);
    }
}
