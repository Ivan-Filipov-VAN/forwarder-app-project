package com.softuni.forwardingApp.repositories;

import com.softuni.forwardingApp.models.entity.AgentEntity;
import com.softuni.forwardingApp.models.view.AgentViewIdName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<AgentEntity, Long> {

    @Query("SELECT new com.softuni.forwardingApp.models.view.AgentViewIdName(a.id, a.name)" +
            " FROM AgentEntity a")
    List<AgentViewIdName> findAllAgentsByIdName();

    List<AgentEntity> findAll();
}
