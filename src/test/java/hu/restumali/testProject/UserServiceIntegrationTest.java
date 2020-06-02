package hu.restumali.testProject;

import hu.restumali.testProject.model.UserDTO;
import hu.restumali.testProject.model.UserEntity;
import hu.restumali.testProject.model.UserRoleType;
import hu.restumali.testProject.repository.UserRepository;
import hu.restumali.testProject.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest {

    @TestConfiguration
    private static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService(){
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Before
    public void setUp(){
        UserEntity user = new UserEntity();
        user.setUsername("User 1");
        user.setPassword("12345");
        user.setRoles(List.of(UserRoleType.LoggedInUser, UserRoleType.ContentManager));
        UserEntity savedUser = new UserEntity();
        savedUser.setUsername("User 1");
        savedUser.setPassword(passwordEncoder.encode("12345"));
        savedUser.setRoles(List.of(UserRoleType.LoggedInUser));
        Mockito.when(userRepository.findOneByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(userRepository.findOneByUsername("User 2")).thenReturn(null);
    }

    @Test
    public void whenValidUsername_thenUserEntityShouldBeFound(){
        String username = "User 1";
        UserEntity found = userService.findByUsername(username);

        assertThat(found.getUsername()).isEqualTo(username);
        assertThat(found.getPassword()).isEqualTo("12345");
        assertThat(found.getRoles()).isEqualTo(List.of(UserRoleType.LoggedInUser, UserRoleType.ContentManager));
        assertThat(found.getRoles().contains(UserRoleType.Administrator)).isFalse();
    }

    @Test
    public void whenInvalidUsername_thenNullShouldBeReturned(){
        String username = "User 2";
        UserEntity found = userService.findByUsername(username);

        assertThat(found).isNull();
    }

    @Test
    public void whenNotExistingUsername_thenFalseShouldBeReturned(){
        String username = "User 2";
        Boolean returnValue = userService.usernameExists(username);
        assertThat(returnValue).isFalse();
    }

    @Test
    public void whenExistingUsername_thenTrueShouldBeReturned(){
        String username = "User 1";
        Boolean returnValue = userService.usernameExists(username);
        assertThat(returnValue).isTrue();
    }
}
