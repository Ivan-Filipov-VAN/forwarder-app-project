package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.dto.AgentAddDto;
import com.softuni.forwardingApp.models.dto.AgentUpdateDto;
import com.softuni.forwardingApp.models.entity.AgentEntity;
import com.softuni.forwardingApp.models.view.AgentViewIdName;
import com.softuni.forwardingApp.models.view.AgentViewModel;
import com.softuni.forwardingApp.repositories.AgentRepository;
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
public class AgentServiceTest {

    @Mock
    private AgentRepository mockAgentRepository;

    @Mock
    private ModelMapper mockModelMapper;

    private AgentService serviceToTest;

    private AgentEntity firstAgent;
    private AgentEntity secondAgent;
    private AgentEntity thirdAgent;
    private AgentEntity fourthAgent;

    @BeforeEach
    void setUp() {
        serviceToTest = new AgentService(mockAgentRepository, mockModelMapper);

        firstAgent = new AgentEntity()
                .setName("first")
                .setVat("111111")
                .setCountry("hk")
                .setTown("hhh")
                .setAddress("kkk");
        firstAgent.setId(7L);

        secondAgent = new AgentEntity()
                .setName("second")
                .setVat("222222")
                .setCountry("mg")
                .setTown("mmm")
                .setAddress("ggg");
        secondAgent.setId(13L);

        thirdAgent = new AgentEntity()
                .setName("third")
                .setCountry("tw");
        thirdAgent.setId(17L);

        fourthAgent = new AgentEntity()
                .setName("fourth")
                .setCountry("om");
        fourthAgent.setId(19L);
    }

    @Test
    void testAddCompany() {

        AgentAddDto agentAddDtoFirst = new AgentAddDto()
                .setName(firstAgent.getName())
                .setVat(firstAgent.getVat())
                .setCountry(firstAgent.getCountry())
                .setAddress(firstAgent.getAddress())
                .setTown(firstAgent.getTown());

        AgentAddDto agentAddDtoFourth = new AgentAddDto()
                .setName(fourthAgent.getName())
                .setVat(fourthAgent.getVat())
                .setCountry(fourthAgent.getCountry())
                .setAddress(fourthAgent.getAddress())
                .setTown(fourthAgent.getTown());

        when(mockModelMapper.map(agentAddDtoFirst, AgentEntity.class))
                .thenReturn(firstAgent);

        when(mockModelMapper.map(agentAddDtoFourth, AgentEntity.class))
                .thenReturn(fourthAgent);

        AgentEntity agentToTestFirst = serviceToTest.addAgent(agentAddDtoFirst);
        AgentEntity agentToTestFourth = serviceToTest.addAgent(agentAddDtoFourth);

        Assertions.assertEquals(firstAgent.getName(), agentToTestFirst.getName());
        Assertions.assertEquals(firstAgent.getVat(), agentToTestFirst.getVat());
        Assertions.assertEquals(firstAgent.getCountry(), agentToTestFirst.getCountry());
        Assertions.assertEquals(firstAgent.getAddress(), agentToTestFirst.getAddress());
        Assertions.assertEquals(firstAgent.getTown(), agentToTestFirst.getTown());

        Assertions.assertEquals(fourthAgent.getName(), agentToTestFourth.getName());
        Assertions.assertEquals(fourthAgent.getVat(), agentToTestFourth.getVat());
        Assertions.assertEquals(fourthAgent.getCountry(), agentToTestFourth.getCountry());
        Assertions.assertEquals(fourthAgent.getAddress(), agentToTestFourth.getAddress());
        Assertions.assertEquals(fourthAgent.getTown(), agentToTestFourth.getTown());

    }

    @Test
    void testFindAllAgents() {
        AgentViewModel secondAgentView = new AgentViewModel()
                .setName("second")
                .setVat("222222")
                .setCountry("mg")
                .setTown("mmm")
                .setAddress("ggg");
        secondAgent.setId(13L);

        AgentViewModel thirdAgentView = new AgentViewModel()
                .setName("third")
                .setCountry("tw");
        thirdAgent.setId(17L);

        when(mockAgentRepository.findAll()).
                thenReturn(List.of(secondAgent, thirdAgent));

        when(mockModelMapper.map(secondAgent, AgentViewModel.class))
                .thenReturn(secondAgentView);

        when(mockModelMapper.map(thirdAgent, AgentViewModel.class))
                .thenReturn(thirdAgentView);

        List<AgentViewModel> allAgents = serviceToTest.findAllAgents();

        Assertions.assertEquals(2, allAgents.size());

        Assertions.assertEquals(secondAgent.getName(), allAgents.get(0).getName());
        Assertions.assertEquals(secondAgent.getVat(), allAgents.get(0).getVat());
        Assertions.assertEquals(secondAgent.getCountry(), allAgents.get(0).getCountry());
        Assertions.assertEquals(secondAgent.getAddress(), allAgents.get(0).getAddress());
        Assertions.assertEquals(secondAgent.getTown(), allAgents.get(0).getTown());

        Assertions.assertEquals(thirdAgent.getName(), allAgents.get(1).getName());
        Assertions.assertEquals(thirdAgent.getVat(), allAgents.get(1).getVat());
        Assertions.assertEquals(thirdAgent.getCountry(), allAgents.get(1).getCountry());
        Assertions.assertEquals(thirdAgent.getAddress(), allAgents.get(1).getAddress());
        Assertions.assertEquals(thirdAgent.getTown(), allAgents.get(1).getTown());

    }

    @Test
    void testGetAgentUpdateDtoById() {

        AgentUpdateDto secondAgentUpdate = new AgentUpdateDto()
                .setName("second")
                .setVat("222222")
                .setCountry("mg")
                .setTown("mmm")
                .setAddress("ggg");
        secondAgentUpdate.setId(13L);

        AgentUpdateDto thirdAgentUpdate = new AgentUpdateDto()
                .setName("third")
                .setCountry("tw");
        thirdAgentUpdate.setId(17L);

        when(mockAgentRepository.findById(13L)).
                thenReturn(Optional.ofNullable(secondAgent));

        when(mockAgentRepository.findById(17L)).
                thenReturn(Optional.ofNullable(thirdAgent));


        when(mockModelMapper.map(secondAgent, AgentUpdateDto.class))
                .thenReturn(secondAgentUpdate);

        when(mockModelMapper.map(thirdAgent, AgentUpdateDto.class))
                .thenReturn(thirdAgentUpdate);

        AgentUpdateDto second = serviceToTest.getById(13L);
        AgentUpdateDto third = serviceToTest.getById(17L);

        Assertions.assertEquals(secondAgent.getName(), second.getName());
        Assertions.assertEquals(secondAgent.getVat(), second.getVat());
        Assertions.assertEquals(secondAgent.getCountry(), second.getCountry());
        Assertions.assertEquals(secondAgent.getAddress(), second.getAddress());
        Assertions.assertEquals(secondAgent.getTown(), second.getTown());
        Assertions.assertEquals(secondAgent.getId(), second.getId());

        Assertions.assertEquals(thirdAgent.getName(), third.getName());
        Assertions.assertEquals(thirdAgent.getVat(), third.getVat());
        Assertions.assertEquals(thirdAgent.getCountry(), third.getCountry());
        Assertions.assertEquals(thirdAgent.getAddress(), third.getAddress());
        Assertions.assertEquals(thirdAgent.getTown(), third.getTown());
        Assertions.assertEquals(thirdAgent.getId(), third.getId());

    }

    @Test
    void testAgentUpdate() {

        AgentUpdateDto secondAgentUpdate = new AgentUpdateDto()
                .setName("second")
                .setVat("222222")
                .setCountry("mg")
                .setTown("mmm")
                .setAddress("ggg");
        secondAgentUpdate.setId(13L);

        AgentUpdateDto thirdAgentUpdate = new AgentUpdateDto()
                .setName("third")
                .setCountry("tw");
        thirdAgentUpdate.setId(17L);

        when(mockModelMapper.map(secondAgentUpdate, AgentEntity.class))
                .thenReturn(secondAgent);

        when(mockModelMapper.map(thirdAgentUpdate, AgentEntity.class))
                .thenReturn(thirdAgent);

        AgentEntity second = serviceToTest.save(secondAgentUpdate);
        AgentEntity third = serviceToTest.save(thirdAgentUpdate);

        Assertions.assertEquals(secondAgent.getName(), second.getName());
        Assertions.assertEquals(secondAgent.getVat(), second.getVat());
        Assertions.assertEquals(secondAgent.getCountry(), second.getCountry());
        Assertions.assertEquals(secondAgent.getAddress(), second.getAddress());
        Assertions.assertEquals(secondAgent.getTown(), second.getTown());
        Assertions.assertEquals(secondAgent.getId(), second.getId());

        Assertions.assertEquals(thirdAgent.getName(), third.getName());
        Assertions.assertEquals(thirdAgent.getVat(), third.getVat());
        Assertions.assertEquals(thirdAgent.getCountry(), third.getCountry());
        Assertions.assertEquals(thirdAgent.getAddress(), third.getAddress());
        Assertions.assertEquals(thirdAgent.getTown(), third.getTown());
        Assertions.assertEquals(thirdAgent.getId(), third.getId());
    }

    @Test
    void testFindAllAgentsByIdName() {

        AgentViewIdName first = new AgentViewIdName(18L, "pesho")
                .setName(firstAgent.getName())
                .setId(firstAgent.getId());

        AgentViewIdName third = new AgentViewIdName(44L, "gosho")
                .setName(thirdAgent.getName())
                .setId(thirdAgent.getId());

        AgentViewIdName fourth = new AgentViewIdName(44L, "gosho")
                .setName(fourthAgent.getName())
                .setId(fourthAgent.getId());

        when(mockAgentRepository.findAllAgentsByIdName()).
                thenReturn(List.of(first, third, fourth));

        List<AgentViewIdName> allAgents = serviceToTest.findAllAgentsByIdName();

        Assertions.assertEquals(3, allAgents.size());

        Assertions.assertEquals(firstAgent.getId(), allAgents.get(0).getId());
        Assertions.assertEquals(firstAgent.getName(), allAgents.get(0).getName());

        Assertions.assertEquals(thirdAgent.getId(), allAgents.get(1).getId());
        Assertions.assertEquals(thirdAgent.getName(), allAgents.get(1).getName());

        Assertions.assertEquals(fourthAgent.getId(), allAgents.get(2).getId());
        Assertions.assertEquals(fourthAgent.getName(), allAgents.get(2).getName());

    }

    @Test
    void testFindAgentsById() {

        when(mockAgentRepository.findById(13L)).
                thenReturn(Optional.ofNullable(secondAgent));

        AgentEntity agentEntity = serviceToTest.findById(13L);

        Assertions.assertEquals(secondAgent.getName(), agentEntity.getName());
        Assertions.assertEquals(secondAgent.getVat(), agentEntity.getVat());
        Assertions.assertEquals(secondAgent.getCountry(), agentEntity.getCountry());
        Assertions.assertEquals(secondAgent.getAddress(), agentEntity.getAddress());
        Assertions.assertEquals(secondAgent.getTown(), agentEntity.getTown());
        Assertions.assertEquals(secondAgent.getId(), agentEntity.getId());

    }

    @Test
    void testFindAgentsByIdNull() {

        AgentEntity agentEntity = serviceToTest.findById(555L);

        Assertions.assertNull(agentEntity);

    }

}
