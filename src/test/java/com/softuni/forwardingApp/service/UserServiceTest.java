package com.softuni.forwardingApp.service;

import com.softuni.forwardingApp.models.entity.CompanyEntity;
import com.softuni.forwardingApp.models.entity.UserEntity;
import com.softuni.forwardingApp.models.entity.UserRoleEntity;
import com.softuni.forwardingApp.models.enums.UserRoleEnum;
import com.softuni.forwardingApp.models.view.UserViewModel;
import com.softuni.forwardingApp.models.view.UserViewModelToSetRoles;
import com.softuni.forwardingApp.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private ModelMapper mockModelMapper;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private CompanyService mockCompanyService;
    @Mock
    private UserDetailsService mockUserDetailsService;
    @Mock
    private EmailService mockEmailService;

    private UserService serviceToTest;

    public UserServiceTest() {
    }

    @BeforeEach
    void setUp() {
        serviceToTest = new UserService(
                mockModelMapper,
                mockPasswordEncoder,
                mockUserRepository,
                mockCompanyService,
                mockUserDetailsService,
                mockEmailService);
    }

    @Test
    void testFindAllUsersWithoutRoles() {

        CompanyEntity testCompany = new CompanyEntity()
                .setName("company")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");

        UserEntity testFirstUser =
                new UserEntity()
                        .setFirstName("pesho")
                        .setLastName("gosho")
                        .setEmail("pesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(testCompany)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles(List.of());
        UserEntity testSecondUser =
                new UserEntity()
                        .setFirstName("mesho")
                        .setLastName("gosho")
                        .setEmail("mesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(testCompany)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles(List.of()
                        );

        when(mockUserRepository.findByUserRolesEmpty())
                .thenReturn(List.of(testFirstUser, testSecondUser));

        List<UserEntity> allNewUsers = serviceToTest.findAllNewUsers();

        Assertions.assertEquals(2, allNewUsers.size());

        Assertions.assertEquals("pesho", allNewUsers.get(0).getFirstName());
        Assertions.assertEquals("gosho", allNewUsers.get(0).getLastName());
        Assertions.assertEquals("pesho@gosho.com", allNewUsers.get(0).getEmail());
        Assertions.assertEquals("1111", allNewUsers.get(0).getPassword());
        Assertions.assertEquals("11111111", allNewUsers.get(0).getPhoneNumber());
        Assertions.assertEquals("company", allNewUsers.get(0).getCompany().getName());
        Assertions.assertEquals("111111", allNewUsers.get(0).getCompany().getVat());
        Assertions.assertEquals("pesho", allNewUsers.get(0).getCompany().getAccountablePerson());
        Assertions.assertEquals("mladost", allNewUsers.get(0).getCompany().getAddress());
        Assertions.assertEquals("company", allNewUsers.get(0).getCompName());
        Assertions.assertEquals("111111", allNewUsers.get(0).getCompVAT());
        List<UserRoleEntity> userRoles = allNewUsers.get(0).getUserRoles();
        Assertions.assertEquals(0, userRoles.size());

        Assertions.assertEquals("mesho", allNewUsers.get(1).getFirstName());
        Assertions.assertEquals("gosho", allNewUsers.get(1).getLastName());
        Assertions.assertEquals("mesho@gosho.com", allNewUsers.get(1).getEmail());
        Assertions.assertEquals("1111", allNewUsers.get(1).getPassword());
        Assertions.assertEquals("11111111", allNewUsers.get(1).getPhoneNumber());
        Assertions.assertEquals("company", allNewUsers.get(1).getCompany().getName());
        Assertions.assertEquals("111111", allNewUsers.get(1).getCompany().getVat());
        Assertions.assertEquals("pesho", allNewUsers.get(1).getCompany().getAccountablePerson());
        Assertions.assertEquals("mladost", allNewUsers.get(1).getCompany().getAddress());
        Assertions.assertEquals("company", allNewUsers.get(1).getCompName());
        Assertions.assertEquals("111111", allNewUsers.get(1).getCompVAT());
        List<UserRoleEntity> userRoles2 = allNewUsers.get(1).getUserRoles();
        Assertions.assertEquals(0, userRoles2.size());


    }

    @Test
    void testFindUserById() {

        CompanyEntity testCompany = new CompanyEntity()
                .setName("company")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");

        UserEntity testFourthUser =
                new UserEntity()
                        .setFirstName("fesho")
                        .setLastName("gosho")
                        .setEmail("fesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(testCompany)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles(List.of(
                                        new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN),
                                        new UserRoleEntity().setUserRole(UserRoleEnum.EMPLOYEE)
                                )
                        );
        testFourthUser.setId(65L);

        when(mockUserRepository.findById(testFourthUser.getId()))
                .thenReturn(Optional.of(testFourthUser));

        UserEntity testUser = serviceToTest.findById(testFourthUser.getId());

        Assertions.assertEquals("fesho", testUser.getFirstName());
        Assertions.assertEquals("gosho", testUser.getLastName());
        Assertions.assertEquals("fesho@gosho.com", testUser.getEmail());
        Assertions.assertEquals("1111", testUser.getPassword());
        Assertions.assertEquals("11111111", testUser.getPhoneNumber());
        Assertions.assertEquals("company", testUser.getCompany().getName());
        Assertions.assertEquals("111111", testUser.getCompany().getVat());
        Assertions.assertEquals("pesho", testUser.getCompany().getAccountablePerson());
        Assertions.assertEquals("mladost", testUser.getCompany().getAddress());
        Assertions.assertEquals("company", testUser.getCompName());
        Assertions.assertEquals("111111", testUser.getCompVAT());

        List<UserRoleEntity> userRoles = testUser.getUserRoles();

        Assertions.assertEquals(2, userRoles.size());

        Assertions.assertEquals("ADMIN", testUser.getUserRoles().get(0).getUserRole().name());
        Assertions.assertEquals("EMPLOYEE", testUser.getUserRoles().get(1).getUserRole().name());
    }

    @Test
    void testFindUserByIdWithWrongId() {
        UserEntity user = serviceToTest.findById(4L);
        Assertions.assertNull(user);
    }

    @Test
    void testModelMapper() {

        CompanyEntity testCompany = new CompanyEntity()
                .setName("company")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");

        testCompany.setId(99L);

        UserEntity testFirstUser =
                new UserEntity()
                        .setFirstName("pesho")
                        .setLastName("gosho")
                        .setEmail("pesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(testCompany)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles(List.of());

        testFirstUser.setId(55L);

        UserViewModelToSetRoles resultTestUser =
                new UserViewModelToSetRoles()
                        .setFirstName("pesho")
                        .setLastName("gosho")
                        .setEmail("pesho@gosho.com")
                        .setPhoneNumber("11111111")
                        .setIdCompany(99L)
                        .setUserRoles(List.of());

        resultTestUser.setId(55L);

        when(mockModelMapper.map(testFirstUser, UserViewModelToSetRoles.class)).
                thenReturn(resultTestUser);


        UserViewModelToSetRoles userViewModelToSetRoles =
                mockModelMapper.map(testFirstUser, UserViewModelToSetRoles.class);

        Assertions.assertEquals(testFirstUser.getFirstName(), userViewModelToSetRoles.getFirstName());
        Assertions.assertEquals(testFirstUser.getLastName(), userViewModelToSetRoles.getLastName());
        Assertions.assertEquals(testFirstUser.getEmail(), userViewModelToSetRoles.getEmail());
        Assertions.assertEquals(testFirstUser.getPhoneNumber(), userViewModelToSetRoles.getPhoneNumber());
        Assertions.assertEquals(testFirstUser.getCompany().getId(), userViewModelToSetRoles.getIdCompany());
        Assertions.assertEquals(testFirstUser.getId(), userViewModelToSetRoles.getId());
        Assertions.assertEquals(testFirstUser.getUserRoles().size(), userViewModelToSetRoles.getUserRoles().size());


    }

    @Test
    void testSetUserRoles() {

        CompanyEntity testCompany = new CompanyEntity()
                .setName("company")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");

        testCompany.setId(99L);

        UserEntity testFourthUser =
                new UserEntity()
                        .setFirstName("fesho")
                        .setLastName("gosho")
                        .setEmail("fesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(null)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles(List.of());
        testFourthUser.setId(65L);

        UserViewModelToSetRoles userViewModelToSetRoles = new UserViewModelToSetRoles()
                .setEmail("fesho@gosho.com")
                .setFirstName("fesho")
                .setLastName("gosho")
                .setPhoneNumber("11111111")
                .setIdCompany(99L)
                .setId(65L)
                .setUserRoles(List.of(
                        new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN),
                        new UserRoleEntity().setUserRole(UserRoleEnum.EMPLOYEE)));


        when(mockUserRepository.findById(testFourthUser.getId()))
                .thenReturn(Optional.of(testFourthUser));

        when(mockCompanyService.findById(testCompany.getId()))
                .thenReturn(testCompany);

        serviceToTest.setUserRoleAndCompany(userViewModelToSetRoles);

        Assertions.assertEquals(2, testFourthUser.getUserRoles().size());
        Assertions.assertEquals(99L, testFourthUser.getCompany().getId());

        List<UserRoleEntity> userRoles = testFourthUser.getUserRoles();

        Assertions.assertEquals(userViewModelToSetRoles.getUserRoles().get(0), userRoles.get(0));
        Assertions.assertEquals(userViewModelToSetRoles.getUserRoles().get(1), userRoles.get(1));


    }

    @Test
    void testSetUserRolesCompanyNull() {

        CompanyEntity testCompany = new CompanyEntity()
                .setName("company")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");

        testCompany.setId(99L);

        UserEntity testFourthUser =
                new UserEntity()
                        .setFirstName("fesho")
                        .setLastName("gosho")
                        .setEmail("fesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(null)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles(List.of());
        testFourthUser.setId(65L);

        UserViewModelToSetRoles userViewModelToSetRoles = new UserViewModelToSetRoles()
                .setEmail("fesho@gosho.com")
                .setFirstName("fesho")
                .setLastName("gosho")
                .setPhoneNumber("11111111")
                .setIdCompany(null)
                .setId(65L)
                .setUserRoles(List.of(
                        new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN),
                        new UserRoleEntity().setUserRole(UserRoleEnum.EMPLOYEE)));


        when(mockUserRepository.findById(testFourthUser.getId()))
                .thenReturn(Optional.of(testFourthUser));

        serviceToTest.setUserRoleAndCompany(userViewModelToSetRoles);

        Assertions.assertEquals(2, testFourthUser.getUserRoles().size());
        Assertions.assertNull(testFourthUser.getCompany());

        List<UserRoleEntity> userRoles = testFourthUser.getUserRoles();

        Assertions.assertEquals(userViewModelToSetRoles.getUserRoles().get(0), userRoles.get(0));
        Assertions.assertEquals(userViewModelToSetRoles.getUserRoles().get(1), userRoles.get(1));


    }

    @Test
    void testFindAllUserViewModels() {

        CompanyEntity testCompany = new CompanyEntity()
                .setName("company")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");

        UserEntity testFirstUser =
                new UserEntity()
                        .setFirstName("pesho")
                        .setLastName("gosho")
                        .setEmail("pesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(testCompany)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles(List.of());
        testFirstUser.setId(33L);

        UserEntity testSecondUser =
                new UserEntity()
                        .setFirstName("mesho")
                        .setLastName("gosho")
                        .setEmail("mesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(testCompany)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles(List.of()
                        );
        testSecondUser.setId(43L);

        UserViewModel resultTestFirst = new UserViewModel()
                .setId(testFirstUser.getId())
                .setEmail(testFirstUser.getEmail())
                .setFirstName(testFirstUser.getFirstName())
                .setLastName(testFirstUser.getLastName())
                .setPhoneNumber(testFirstUser.getPhoneNumber())
                .setUserRoles(testFirstUser.getUserRoles())
                .setCompany(testCompany);

        UserViewModel resultTestSecond = new UserViewModel()
                .setId(testSecondUser.getId())
                .setEmail(testSecondUser.getEmail())
                .setFirstName(testSecondUser.getFirstName())
                .setLastName(testSecondUser.getLastName())
                .setPhoneNumber(testSecondUser.getPhoneNumber())
                .setUserRoles(testSecondUser.getUserRoles())
                .setCompany(testCompany);


        when(mockUserRepository.findAll())
                .thenReturn(List.of(testFirstUser, testSecondUser));

        when(mockModelMapper.map(testFirstUser, UserViewModel.class)).
                thenReturn(resultTestFirst);
        when(mockModelMapper.map(testSecondUser, UserViewModel.class)).
                thenReturn(resultTestSecond);


//        private Long id;
//        private String email;
//        private String firstName;
//        private String lastName;
//        private String phoneNumber;
//        private List<UserRoleEntity> userRoles = new ArrayList<>();
//        private CompanyEntity company;

        List<UserViewModel> allUserViewModels = serviceToTest.findAllUserViewModels();

        Assertions.assertEquals(2, allUserViewModels.size());
        Assertions.assertEquals(testFirstUser.getEmail(), allUserViewModels.get(0).getEmail());
        Assertions.assertEquals(testSecondUser.getEmail(), allUserViewModels.get(1).getEmail());

    }

    @Test
    void testFindUserViewById() {

        CompanyEntity testCompany = new CompanyEntity()
                .setName("company")
                .setVat("111111")
                .setAccountablePerson("pesho")
                .setAddress("mladost");

        UserEntity testFirstUser =
                new UserEntity()
                        .setFirstName("pesho")
                        .setLastName("gosho")
                        .setEmail("pesho@gosho.com")
                        .setPassword("1111")
                        .setPhoneNumber("11111111")
                        .setCompany(testCompany)
                        .setCompName("company")
                        .setCompVAT("111111")
                        .setUserRoles(List.of());
        testFirstUser.setId(33L);

        UserViewModelToSetRoles userViewModelToSetRoles =
                new UserViewModelToSetRoles()
                        .setId(testFirstUser.getId())
                        .setFirstName(testFirstUser.getFirstName())
                        .setLastName(testFirstUser.getLastName())
                        .setEmail(testFirstUser.getEmail())
                        .setPhoneNumber(testFirstUser.getPhoneNumber())
                        .setIdCompany(testFirstUser.getCompany().getId())
                        .setUserRoles(testFirstUser.getUserRoles());

        when(mockUserRepository.findById(testFirstUser.getId()))
                .thenReturn(Optional.of(testFirstUser));


        when(mockModelMapper.map(testFirstUser, UserViewModelToSetRoles.class)).
                thenReturn(userViewModelToSetRoles);

        UserViewModelToSetRoles result = serviceToTest.findUserViewById(testFirstUser.getId());

        Assertions.assertEquals(testFirstUser.getUserRoles().size(), result.getUserRoles().size());
        Assertions.assertEquals(testFirstUser.getId(), result.getId());
        Assertions.assertEquals(testFirstUser.getFirstName(), result.getFirstName());
        Assertions.assertEquals(testFirstUser.getLastName(), result.getLastName());
        Assertions.assertEquals(testFirstUser.getPhoneNumber(), result.getPhoneNumber());
        Assertions.assertEquals(testFirstUser.getCompany().getId(), result.getIdCompany());

    }


}
