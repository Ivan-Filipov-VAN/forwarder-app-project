package com.softuni.forwardingApp.models.mapper;

import com.softuni.forwardingApp.models.dto.DealAddDto;
import com.softuni.forwardingApp.models.dto.DealUpdateDto;
import com.softuni.forwardingApp.models.entity.DealEntity;
import com.softuni.forwardingApp.models.view.DealViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = {LocalDate.class})
public interface DealMapper {

    @Mapping(target = "date", defaultExpression = "java(LocalDate.now())")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "company", ignore = true)
    @Mapping(target = "agent", ignore = true)
    @Mapping(target = "employee", ignore = true)
    DealEntity dealAddDtoToDealEntity(DealAddDto dealAddDto);

    DealUpdateDto dealEntityToDealUpdateDto(DealEntity dealEntity);

    DealEntity dealUpdateDtoToDealEntity(DealUpdateDto dealUpdateDto);

    @Mapping(target = "company", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "agent", ignore = true)
    DealViewModel dealEntityToDealViewModel(DealEntity d);

}
