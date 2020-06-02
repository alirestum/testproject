package hu.restumali.testProject.service;

import hu.restumali.testProject.model.UserDTO;
import hu.restumali.testProject.model.UserEntity;
import hu.restumali.testProject.model.UserRoleType;
import hu.restumali.testProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity findByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    public void registerNewUser(UserDTO newUser) {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userEntity.setUsername(newUser.getUsername());
        userEntity.setRoles(List.of(UserRoleType.LoggedInUser));
        userRepository.save(userEntity);
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public boolean usernameExists(String username) {
        return userRepository.findOneByUsername(username) != null;
    }
}
