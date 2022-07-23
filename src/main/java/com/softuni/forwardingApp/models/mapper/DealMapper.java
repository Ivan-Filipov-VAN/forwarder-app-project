package com.softuni.forwardingApp.models.mapper;

import com.softuni.forwardingApp.models.dto.DealAddDto;
import com.softuni.forwardingApp.models.dto.DealUpdateDto;
import com.softuni.forwardingApp.models.entity.DealEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = {LocalDate.class})
public interface DealMapper {

    @Mapping(target = "date", defaultExpression = "java(LocalDate.now())")
    DealEntity dealAddDtoToDealEntity(DealAddDto dealAddDto);

    DealUpdateDto dealEntityToDealUpdateDto(DealEntity dealEntity);

    DealEntity dealEntityToDealUpdateDto(DealUpdateDto dealUpdateDto);

}
