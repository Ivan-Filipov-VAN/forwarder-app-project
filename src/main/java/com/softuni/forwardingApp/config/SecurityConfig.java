package com.softuni.forwardingApp.config;

import com.softuni.forwardingApp.models.enums.UserRoleEnum;
import com.softuni.forwardingApp.repositories.UserRepository;
import com.softuni.forwardingApp.service.CurrentUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.
                        authorizeRequests().
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().
                        antMatchers("/", "/users/login", "/users/register").permitAll().
                        antMatchers("/companies/add-company").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/companies/all-company").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/companies/edit/{id}").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/companies/save").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/agents/add-agent").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/agents/all-agent").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/agents/save").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/users/new-users").hasRole(UserRoleEnum.ADMIN.name()).
                        antMatchers("/users/editAddRoleCompany").hasRole(UserRoleEnum.ADMIN.name()).
                        antMatchers("/users/editAddRoleCompany/{id}").hasRole(UserRoleEnum.ADMIN.name()).
                        antMatchers("/users/all-users").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/deals/add-deal").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/deals/all-deals", "/deals/all-deals-in-transit", "/change-status-in-transit/{id}").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/deals/update").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/deals/edit/{id}").hasRole(UserRoleEnum.EMPLOYEE.name()).
                        antMatchers("/deals/customer-deals", "/deals/customer-all-deals").hasRole(UserRoleEnum.CUSTOMER.name()).
                        anyRequest().
                authenticated().
                and().
                        formLogin().
                        loginPage("/users/login").
                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                        defaultSuccessUrl("/").
                        failureForwardUrl("/users/login-error").
                and().
                        logout().
                        logoutUrl("/users/logout").
                        logoutSuccessUrl("/").
                        invalidateHttpSession(true).
                deleteCookies("JSESSIONID");


        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new CurrentUserDetailsService(userRepository);
    }

}
