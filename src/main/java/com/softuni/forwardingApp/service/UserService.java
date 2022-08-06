package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.dto.UserRegisterDto;
import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.models.entity.UserRoleEntity;
import com.softuni.forwardingApp.models.view.UserViewModel;
import com.softuni.forwardingApp.models.view.UserViewModelToSetRoles;
import com.softuni.forwardingApp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final UserDetailsService userDetailsService;
    private final EmailService emailService;

    public UserService(
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            CompanyService companyService,
            UserDetailsService userDetailsService,
            EmailService emailService) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.companyService = companyService;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    public void registerAndLogin(UserRegisterDto userRegisterDto) {

        UserEntity newUser = modelMapper.map(userRegisterDto, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        userRepository.save(newUser);
        emailService.sendRegistrationEmail(userRegisterDto.getEmail(), userRegisterDto.getFirstName() + " " + userRegisterDto.getLastName());
        login(newUser);


    }

    private void login(UserEntity userEntity) {
        UserDetails userDetails =
                userDetailsService.loadUserByUsername(userEntity.getEmail());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.
                getContext().
                setAuthentication(auth);
    }

    public void initAdmin(List<UserRoleEntity> roles, CompanyEntity company) {

        if (userRepository.count() > 0) {
            return;
        }

        UserEntity admin = new UserEntity()
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setEmail("ivan@ivan.com")
                .setPassword(passwordEncoder.encode("1234"))
                .setPhoneNumber("0891556556")
                .setUserRoles(roles)
                .setCompany(company)
                .setCompName("5Ex Cargo")
                .setCompVAT("69696969");

        userRepository.save(admin);
    }

    public void initUsers(List<UserRoleEntity> roles, CompanyEntity company) {

        if (userRepository.count() > 1) {
            return;
        }

        UserEntity employee = new UserEntity()
                .setFirstName("Van")
                .setLastName("Vanov")
                .setEmail("van@van.com")
                .setPassword(passwordEncoder.encode("1234"))
                .setPhoneNumber("0891556566")
                .setUserRoles(List.of(roles.get(0)))
                .setCompany(company)
                .setCompName("5Ex Cargo")
                .setCompVAT("69696969");

        userRepository.save(employee);

        UserEntity client = new UserEntity()
                .setFirstName("Zan")
                .setLastName("Zanov")
                .setEmail("zan@zan.com")
                .setPassword(passwordEncoder.encode("1234"))
                .setPhoneNumber("0891123456")
                .setUserRoles(List.of(roles.get(1)))
                .setCompany(companyService.findById(2L))
                .setCompName("Micro Ltd")
                .setCompVAT("12345678");

        userRepository.save(client);

        UserEntity user = new UserEntity()
                .setFirstName("Man")
                .setLastName("Manov")
                .setEmail("man@man.com")
                .setPassword(passwordEncoder.encode("1234"))
                .setPhoneNumber("0891125656")
                .setUserRoles(List.of())
                .setCompName("Spam Comp")
                .setCompVAT("32432432");

        userRepository.save(user);
    }




    public List<UserEntity> findAllNewUsers() {
        return userRepository.findByUserRolesEmpty();
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserViewModelToSetRoles findUserViewById(Long id) {
        return modelMapper.map(findById(id), UserViewModelToSetRoles.class);
    }

    public void setUserRoleAndCompany(UserViewModelToSetRoles user) {
        UserEntity userSetRolesCompany = userRepository.findById(user.getId()).orElse(null);
        if (user.getIdCompany() != null) {
            userSetRolesCompany.setCompany(companyService.findById(user.getIdCompany()));
        } else {
            userSetRolesCompany.setCompany(null);
        }
        userSetRolesCompany.setUserRoles(user.getUserRoles());
        userRepository.save(userSetRolesCompany);
    }

    public List<UserViewModel> findAllUserViewModels() {
        return userRepository.findAll().
                stream().
                map(u -> modelMapper.map(u, UserViewModel.class))
                .collect(Collectors.toList());
    }
}
