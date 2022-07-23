package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.entity.UserRoleEntity;
import com.softuni.forwardingApp.models.enums.UserRoleEnum;
import com.softuni.forwardingApp.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void initRoles() {
        if (roleRepository.count() != 0) {
            return;
        }
        Arrays.stream(UserRoleEnum.values())
                .forEach(r -> {
                    UserRoleEntity userRole = new UserRoleEntity();
                    userRole.setUserRole(r);
                    roleRepository.save(userRole);
                });
    }

    public UserRoleEntity findByUserRole(UserRoleEnum name) {
        return roleRepository.findByUserRole(name)
                .orElse(null);
    }

    public List<UserRoleEntity> findAll() {
        return roleRepository.findAll();
    }
}
