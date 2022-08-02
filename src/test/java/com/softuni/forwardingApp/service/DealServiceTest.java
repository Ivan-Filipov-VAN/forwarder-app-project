package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.dto.DealAddDto;
import com.softuni.forwardingApp.models.entity.AgentEntity;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.DealEntity;
import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.models.enums.AirTypeEnum;
import com.softuni.forwardingApp.models.enums.ShipmentStatusEnum;
import com.softuni.forwardingApp.models.mapper.DealMapper;
import com.softuni.forwardingApp.repositories.DealRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DealServiceTest {

    @Mock
    private DealRepository mockDealRepository;

    @Mock
    private AgentService mockAgentService;

    @Mock
    private CompanyService mockCompanyService;

    @Mock
    private UserService mockUserService;

    @Mock
    private DealMapper mockDealMapper;

    private DealService serviceToTest;

    private DealEntity firstDeal;
    private DealEntity secondDeal;
    private DealEntity thirdDeal;
    private CompanyEntity companyEntity;

    private AgentEntity agentEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        serviceToTest = new DealService(
                mockDealRepository,
                mockAgentService,
                mockCompanyService,
                mockUserService,
                mockDealMapper);

        companyEntity = new CompanyEntity()
                .setName("first")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");
        companyEntity.setId(4L);

        agentEntity = new AgentEntity()
                .setName("first")
                .setVat("111111")
                .setCountry("hk")
                .setTown("hhh")
                .setAddress("kkk");
        agentEntity.setId(5L);

        CompanyEntity testCompany = new CompanyEntity()
                .setName("company")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");
        testCompany.setId(2L);

        userEntity = new UserEntity()
                        .setFirstName("pesho")
                        .setLastName("gosho")
                        .setEmail("pesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(testCompany)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles(List.of());
        userEntity.setId(9L);


        firstDeal = new DealEntity()
                .setType(AirTypeEnum.IMP)
                .setDate(LocalDate.of(2022, 1, 10))
                .setMawb("34567")
                .setHawb("898989")
                .setCompany(companyEntity)
                .setAgent(agentEntity)
                .setEmployee(userEntity)
                .setPieces(3)
                .setActualWeight(45.0)
                .setChargeableWeight(54.0)
                .setCountry("TW")
                .setAirport("TPE")
                .setStatus(ShipmentStatusEnum.CLEARANCE);
        firstDeal.setId(5L);

        secondDeal = new DealEntity()
                .setType(AirTypeEnum.EXP)
                .setDate(LocalDate.of(2022, 2, 20))
                .setMawb("54321")
                .setHawb("232323")
                .setCompany(companyEntity)
                .setAgent(agentEntity)
                .setEmployee(userEntity)
                .setPieces(4)
                .setActualWeight(55.0)
                .setChargeableWeight(66.0)
                .setCountry("HK")
                .setAirport("HKG")
                .setStatus(ShipmentStatusEnum.SHIPPER_CONTACTED);
        secondDeal.setId(6L);

        thirdDeal = new DealEntity()
                .setType(AirTypeEnum.EXP)
                .setDate(LocalDate.of(2022, 3, 30))
                .setMawb("666666")
                .setHawb("777777")
                .setCompany(companyEntity)
                .setAgent(agentEntity)
                .setEmployee(userEntity)
                .setPieces(5)
                .setActualWeight(77.0)
                .setChargeableWeight(88.0)
                .setCountry("CN")
                .setAirport("BJS")
                .setStatus(ShipmentStatusEnum.SHIPPER_CONTACTED);
        thirdDeal.setId(7L);
    }

    @Test
    void testAddDeal() {

        DealAddDto dealAddDto = new DealAddDto()
                .setType(AirTypeEnum.EXP)
                .setMawb("666666")
                .setHawb("777777")
                .setIdCompany(4L)
                .setIdAgent(5L)
                .setIdEmployee(9L)
                .setPieces(5)
                .setActualWeight(77.0)
                .setChargeableWeight(88.0)
                .setCountry("CN")
                .setAirport("BJS");


        when(mockDealMapper.dealAddDtoToDealEntity(dealAddDto))
                .thenReturn(thirdDeal);

        when(mockAgentService.findById(dealAddDto.getIdAgent()))
                .thenReturn(agentEntity);

        when(mockCompanyService.findById(dealAddDto.getIdCompany()))
                .thenReturn(companyEntity);

        when(mockUserService.findById(dealAddDto.getIdEmployee()))
                .thenReturn(userEntity);

        DealEntity deal = serviceToTest.addDeal(dealAddDto);

        Assertions.assertEquals(thirdDeal.getType(), deal.getType());
        Assertions.assertEquals(thirdDeal.getDate(), deal.getDate());
        Assertions.assertEquals(thirdDeal.getMawb(), deal.getMawb());
        Assertions.assertEquals(thirdDeal.getHawb(), deal.getHawb());
        Assertions.assertEquals(thirdDeal.getCompany(), deal.getCompany());
        Assertions.assertEquals(thirdDeal.getAgent(), deal.getAgent());
        Assertions.assertEquals(thirdDeal.getEmployee(), deal.getEmployee());
        Assertions.assertEquals(thirdDeal.getPieces(), deal.getPieces());
        Assertions.assertEquals(thirdDeal.getActualWeight(), deal.getActualWeight());
        Assertions.assertEquals(thirdDeal.getChargeableWeight(), deal.getChargeableWeight());
        Assertions.assertEquals(thirdDeal.getCountry(), deal.getCountry());
        Assertions.assertEquals(thirdDeal.getAirport(), deal.getAirport());
        Assertions.assertEquals(ShipmentStatusEnum.SHIPPER_CONTACTED, deal.getStatus());



        Assertions.assertEquals(dealAddDto.getType(), deal.getType());
        Assertions.assertNull(dealAddDto.getDate());
        Assertions.assertEquals(dealAddDto.getMawb(), deal.getMawb());
        Assertions.assertEquals(dealAddDto.getHawb(), deal.getHawb());
        Assertions.assertEquals(dealAddDto.getIdCompany(), deal.getCompany().getId());
        Assertions.assertEquals(dealAddDto.getIdAgent(), deal.getAgent().getId());
        Assertions.assertEquals(dealAddDto.getIdEmployee(), deal.getEmployee().getId());
        Assertions.assertEquals(dealAddDto.getPieces(), deal.getPieces());
        Assertions.assertEquals(dealAddDto.getActualWeight(), deal.getActualWeight());
        Assertions.assertEquals(dealAddDto.getChargeableWeight(), deal.getChargeableWeight());
        Assertions.assertEquals(dealAddDto.getCountry(), deal.getCountry());
        Assertions.assertEquals(dealAddDto.getAirport(), deal.getAirport());

    }




}
