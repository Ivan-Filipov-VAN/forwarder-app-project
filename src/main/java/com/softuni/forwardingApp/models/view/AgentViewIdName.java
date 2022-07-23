package com.softuni.forwardingApp.models.view;

public class AgentViewIdName {
    private Long id;
    private String name;

    public AgentViewIdName(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public AgentViewIdName setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AgentViewIdName setName(String name) {
        this.name = name;
        return this;
    }
}
