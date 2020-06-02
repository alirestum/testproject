package hu.restumali.testProject.config;

import hu.restumali.testProject.model.UserDTO;
import hu.restumali.testProject.model.UserEntity;
import hu.restumali.testProject.model.UserRoleType;
import hu.restumali.testProject.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * When h2 db profile is active this class populates the in memory db with the data specified in the project description.
 */
@Component
@Profile("db-h2")
public class H2DbConfig {

    private static final Logger logger = LoggerFactory.getLogger(H2DbConfig.class);

    @Autowired
    UserService userService;

    @PostConstruct
    public void loadDataIntoDB() {
        logger.warn("Loading data into database, please wait until the task finishes!");

        UserDTO user1 = new UserDTO("User 1", "12345");
        UserDTO user2 = new UserDTO("User 2", "12345");
        UserDTO user3 = new UserDTO("User 3", "12345");
        UserDTO admin = new UserDTO("Admin", "12345");
        userService.registerNewUser(user1);
        userService.registerNewUser(user2);
        userService.registerNewUser(user3);
        userService.registerNewUser(admin);
        UserEntity user = userService.findByUsername("User 1");
        user.setRoles(List.of(UserRoleType.LoggedInUser, UserRoleType.ContentManager));
        userService.save(user);

        user = userService.findByUsername("User 2");
        user.setRoles(List.of(UserRoleType.ContentManager));
        userService.save(user);

        user = userService.findByUsername("User 3");
        user.setRoles(List.of(UserRoleType.LoggedInUser));
        userService.save(user);

        user = userService.findByUsername("Admin");
        user.setRoles(List.of(UserRoleType.Administrator));
        userService.save(user);

        logger.info("Test data loaded into DB!");
    }
}
