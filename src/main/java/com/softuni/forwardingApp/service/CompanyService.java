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

        companyRepository.save(new CompanyEntity().setName("Micro Ltd").setVat("12345678"));
        companyRepository.save(new CompanyEntity().setName("Integrated Ltd").setVat("22345678"));
        companyRepository.save(new CompanyEntity().setName("Mercury Ltd").setVat("32345678"));
        companyRepository.save(new CompanyEntity().setName("Venus Ltd").setVat("42345678"));
        companyRepository.save(new CompanyEntity().setName("Earth Ltd").setVat("52345678"));
        companyRepository.save(new CompanyEntity().setName("Mars Ltd").setVat("62345678"));
        companyRepository.save(new CompanyEntity().setName("Jupiter Ltd").setVat("72345678"));
        companyRepository.save(new CompanyEntity().setName("Saturn Ltd").setVat("82345678"));
        companyRepository.save(new CompanyEntity().setName("Uranus Ltd").setVat("92345678"));
        companyRepository.save(new CompanyEntity().setName("Neptune Ltd").setVat("13345678"));
        companyRepository.save(new CompanyEntity().setName("Pluto Ltd").setVat("14345678"));

        return company;
    }

    public CompanyEntity addCompany(CompanyAddDto companyAddDto) {
        CompanyEntity company = modelMapper.map(companyAddDto, CompanyEntity.class);
        companyRepository.save(company);
        return company;
    }

    public List<CompanyEntity> findAll() {
        return companyRepository.findAll();
    }

    public CompanyUpdateDto getById(Long id) {
        CompanyEntity company = companyRepository.findById(id).orElse(null);
        return modelMapper.map(company, CompanyUpdateDto.class);
    }


    public CompanyEntity save(CompanyUpdateDto company) {
        CompanyEntity companyEntity = modelMapper.map(company, CompanyEntity.class);
        companyRepository.save(companyEntity);
        return companyEntity;
    }

    public CompanyEntity findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
