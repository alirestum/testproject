package hu.restumali.testProject;

import hu.restumali.testProject.model.UserEntity;
import hu.restumali.testProject.model.UserRoleType;
import hu.restumali.testProject.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername() {
        UserEntity user = new UserEntity();
        user.setUsername("User 1");
        user.setPassword("12345");
        user.setRoles(List.of(UserRoleType.ContentManager, UserRoleType.Administrator));
        entityManager.persist(user);
        entityManager.flush();

        UserEntity found = userRepository.findOneByUsername("User 1");

        assertThat(found.getUsername()).isEqualTo("User 1");
        assertThat(found.getPassword()).isEqualTo("12345");
        assertThat(found.getRoles().contains(UserRoleType.Administrator)).isTrue();
        assertThat(found.getRoles().contains(UserRoleType.ContentManager)).isTrue();
        assertThat(found.getRoles().contains(UserRoleType.LoggedInUser)).isFalse();
    }
}
