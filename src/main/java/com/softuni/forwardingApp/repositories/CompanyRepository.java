package com.softuni.forwardingApp.repositories;

import com.softuni.forwardingApp.models.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    Optional<CompanyEntity> findByName(String name);
    Optional<CompanyEntity> findByVat(String vat);
}
