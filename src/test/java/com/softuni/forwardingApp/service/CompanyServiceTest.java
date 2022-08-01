package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.dto.CompanyAddDto;
import com.softuni.forwardingApp.models.dto.CompanyUpdateDto;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.repositories.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    private CompanyRepository mockCompanyRepository;

    @Mock
    private ModelMapper mockModelMapper;

    private CompanyService serviceToTest;

    private CompanyEntity firstCompany;
    private CompanyEntity secondCompany;
    private CompanyEntity thirdCompany;
    private CompanyEntity fourthCompany;

    @BeforeEach
    void setUp() {
        serviceToTest = new CompanyService(mockCompanyRepository, mockModelMapper);
        firstCompany = new CompanyEntity()
                .setName("first")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");
        firstCompany.setId(4L);

        secondCompany = new CompanyEntity()
                .setName("second")
                .setVat("222222")
                .setAccountablePerson("sasho")
                .setAddress("drujba");
        secondCompany.setId(7L);

        thirdCompany = new CompanyEntity()
                .setName("third")
                .setVat("333333")
                .setAccountablePerson("tosho")
                .setAddress("slatina");
        thirdCompany.setId(9L);

        fourthCompany = new CompanyEntity()
                .setName("fourth")
                .setVat("444444");
        fourthCompany.setId(11L);
    }

    @Test
    void findAllCompanies() {

        when(mockCompanyRepository.findAll()).
                thenReturn(List.of(firstCompany, secondCompany, thirdCompany));

        List<CompanyEntity> allCompanies = serviceToTest.findAll();

        Assertions.assertEquals(3, allCompanies.size());
        Assertions.assertEquals(firstCompany.getId(), allCompanies.get(0).getId());
        Assertions.assertEquals(firstCompany.getName(), allCompanies.get(0).getName());
        Assertions.assertEquals(firstCompany.getVat(), allCompanies.get(0).getVat());
        Assertions.assertEquals(firstCompany.getAccountablePerson(), allCompanies.get(0).getAccountablePerson());
        Assertions.assertEquals(firstCompany.getAddress(), allCompanies.get(0).getAddress());

        Assertions.assertEquals(secondCompany.getId(), allCompanies.get(1).getId());
        Assertions.assertEquals(secondCompany.getName(), allCompanies.get(1).getName());
        Assertions.assertEquals(secondCompany.getVat(), allCompanies.get(1).getVat());
        Assertions.assertEquals(secondCompany.getAccountablePerson(), allCompanies.get(1).getAccountablePerson());
        Assertions.assertEquals(secondCompany.getAddress(), allCompanies.get(1).getAddress());

        Assertions.assertEquals(thirdCompany.getId(), allCompanies.get(2).getId());
        Assertions.assertEquals(thirdCompany.getName(), allCompanies.get(2).getName());
        Assertions.assertEquals(thirdCompany.getVat(), allCompanies.get(2).getVat());
        Assertions.assertEquals(thirdCompany.getAccountablePerson(), allCompanies.get(2).getAccountablePerson());
        Assertions.assertEquals(thirdCompany.getAddress(), allCompanies.get(2).getAddress());
    }

    @Test
    void testFindCompanyUpdateDtoById() {

        CompanyUpdateDto firstCompanyUpdateDto = new CompanyUpdateDto()
                .setName("third")
                .setVat("333333")
                .setAccountablePerson("tosho")
                .setAddress("slatina");
        firstCompanyUpdateDto.setId(9L);

        CompanyUpdateDto fourthCompanyUpdateDto = new CompanyUpdateDto()
                .setName("fourth")
                .setVat("444444");
        fourthCompanyUpdateDto.setId(11L);


        when(mockCompanyRepository.findById(9L)).
                thenReturn(Optional.ofNullable(thirdCompany));

        when(mockCompanyRepository.findById(11L)).
                thenReturn(Optional.ofNullable(fourthCompany));

        when(mockModelMapper.map(thirdCompany, CompanyUpdateDto.class))
                .thenReturn(firstCompanyUpdateDto);

        when(mockModelMapper.map(fourthCompany, CompanyUpdateDto.class))
                .thenReturn(fourthCompanyUpdateDto);

        CompanyUpdateDto result = serviceToTest.getById(thirdCompany.getId());
        CompanyUpdateDto result2 = serviceToTest.getById(fourthCompany.getId());

        Assertions.assertEquals(thirdCompany.getId(), result.getId());
        Assertions.assertEquals(thirdCompany.getName(), result.getName());
        Assertions.assertEquals(thirdCompany.getVat(), result.getVat());
        Assertions.assertEquals(thirdCompany.getAccountablePerson(), result.getAccountablePerson());
        Assertions.assertEquals(thirdCompany.getAddress(), result.getAddress());

        Assertions.assertEquals(fourthCompanyUpdateDto.getId(), result2.getId());
        Assertions.assertEquals(fourthCompanyUpdateDto.getName(), result2.getName());
        Assertions.assertEquals(fourthCompanyUpdateDto.getVat(), result2.getVat());
        Assertions.assertEquals(fourthCompanyUpdateDto.getAccountablePerson(), result2.getAccountablePerson());
        Assertions.assertEquals(fourthCompanyUpdateDto.getAddress(), result2.getAddress());
    }

    @Test
    void testFindCompanyEntityById() {

        when(mockCompanyRepository.findById(9L)).
                thenReturn(Optional.ofNullable(thirdCompany));

        CompanyEntity third = serviceToTest.findById(9L);

        Assertions.assertEquals(thirdCompany.getId(), third.getId());
        Assertions.assertEquals(thirdCompany.getName(), third.getName());
        Assertions.assertEquals(thirdCompany.getVat(), third.getVat());
        Assertions.assertEquals(thirdCompany.getAccountablePerson(), third.getAccountablePerson());
        Assertions.assertEquals(thirdCompany.getAddress(), third.getAddress());
    }

    @Test
    void testFindCompanyEntityByIdNotExist() {

        CompanyEntity fake = serviceToTest.findById(55L);
        Assertions.assertNull(fake);

    }

    @Test
    void testSaveCompanyEntity() {

        CompanyUpdateDto companyUpdateDto = new CompanyUpdateDto()
                .setName("second")
                .setVat("222222")
                .setAccountablePerson("sasho")
                .setAddress("drujba");

        CompanyEntity companyEntity = new CompanyEntity()
                .setName("second")
                .setVat("222222")
                .setAccountablePerson("sasho")
                .setAddress("drujba");

        fourthCompany.setId(null);

        when(mockModelMapper.map(companyUpdateDto, CompanyEntity.class))
                .thenReturn(companyEntity);

        CompanyEntity result = serviceToTest.save(companyUpdateDto);

        Assertions.assertEquals(companyEntity.getName(), result.getName());
        Assertions.assertEquals(companyEntity.getVat(), result.getVat());
        Assertions.assertEquals(companyEntity.getAccountablePerson(), result.getAccountablePerson());
        Assertions.assertEquals(companyEntity.getAddress(), result.getAddress());
    }

    @Test
    void testAddCompanyEntity() {

        CompanyAddDto companyAddDto = new CompanyAddDto()
                .setName("second")
                .setVat("222222")
                .setAccountablePerson("sasho")
                .setAddress("drujba");

        CompanyEntity companyEntity = new CompanyEntity()
                .setName("second")
                .setVat("222222")
                .setAccountablePerson("sasho")
                .setAddress("drujba");

        fourthCompany.setId(null);

        when(mockModelMapper.map(companyAddDto, CompanyEntity.class))
                .thenReturn(companyEntity);

        CompanyEntity result = serviceToTest.addCompany(companyAddDto);

        Assertions.assertEquals(companyEntity.getName(), result.getName());
        Assertions.assertEquals(companyEntity.getVat(), result.getVat());
        Assertions.assertEquals(companyEntity.getAccountablePerson(), result.getAccountablePerson());
        Assertions.assertEquals(companyEntity.getAddress(), result.getAddress());
    }

    @Test
    void testInitAdminIfExist() {

        when(mockCompanyRepository.count()).
                thenReturn(1L);

        CompanyEntity company = serviceToTest.initCompany();
        CompanyEntity companyEntity = new CompanyEntity();

        Assertions.assertEquals(companyEntity.getName(), company.getName());
        Assertions.assertEquals(companyEntity.getVat(), company.getVat());
        Assertions.assertEquals(companyEntity.getAccountablePerson(), company.getAccountablePerson());
        Assertions.assertEquals(companyEntity.getAddress(), company.getAddress());


    }
}
