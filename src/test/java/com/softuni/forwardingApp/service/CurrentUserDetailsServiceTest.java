package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.models.entity.UserRoleEntity;
import com.softuni.forwardingApp.models.enums.UserRoleEnum;
import com.softuni.forwardingApp.models.user.CurrentUserDetails;
import com.softuni.forwardingApp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrentUserDetailsServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private CurrentUserDetailsService serviceToTest;

    @BeforeEach
    void setUp() {
        serviceToTest = new CurrentUserDetailsService(mockUserRepository);

    }

    @Test
    void testLoadUserByUsernameUserCorrect() {
        CompanyEntity testCompany = new CompanyEntity()
                .setName("company")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");

        UserEntity testUser =
                new UserEntity()
                        .setFirstName("pesho")
                        .setLastName("gosho")
                        .setEmail("pesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(testCompany)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles( List.of(
                                        new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN),
                                        new UserRoleEntity().setUserRole(UserRoleEnum.EMPLOYEE)
                                )
                        );
        when(mockUserRepository.findByEmail(testUser.getEmail())).
                thenReturn(Optional.of(testUser));

        CurrentUserDetails userDetails = (CurrentUserDetails)
                serviceToTest.loadUserByUsername(testUser.getEmail());

        Assertions.assertEquals(testUser.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(testUser.getFirstName(), userDetails.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(testUser.getLastName(), userDetails.getLastName());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUser.getFirstName() + " " + testUser.getLastName(), userDetails.getFullName());


        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        Assertions.assertEquals(2, authorities.size());

        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();

        Assertions.assertEquals("ROLE_" + UserRoleEnum.ADMIN.name(),
                iterator.next().getAuthority());

        Assertions.assertEquals("ROLE_" + UserRoleEnum.EMPLOYEE.name(),
                iterator.next().getAuthority());

    }

    @Test
    void testLoadUserByUsernameUserNotCorrect() {

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("not@not.com"));
    }
}
