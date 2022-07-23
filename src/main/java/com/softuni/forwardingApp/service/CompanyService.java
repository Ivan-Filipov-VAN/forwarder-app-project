package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.dto.CompanyAddDto;
import com.softuni.forwardingApp.models.dto.CompanyUpdateDto;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.repositories.CompanyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public CompanyService(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    public CompanyEntity initCompany() {
        if (companyRepository.count() > 0) {
            return new CompanyEntity();
        }
        CompanyEntity company = new CompanyEntity()
                .setName("5Ex Cargo")
                .setVat("69696969")
                .setAddress("Sofia Airport")
                .setAccountablePerson("Ivan Ivanov");

        companyRepository.save(company);
        return company;
    }

    public void addCompany(CompanyAddDto companyAddDto) {
        CompanyEntity company = modelMapper.map(companyAddDto, CompanyEntity.class);

        companyRepository.save(company);
    }

    public List<CompanyEntity> findAll() {
        return companyRepository.findAll();
    }

    public CompanyUpdateDto getById(Long id) {
        CompanyEntity company = companyRepository.findById(id).orElse(null);
        return modelMapper.map(company, CompanyUpdateDto.class);
    }


    public void save(CompanyUpdateDto company) {
        companyRepository.save(modelMapper.map(company, CompanyEntity.class));
    }

    public CompanyEntity findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
