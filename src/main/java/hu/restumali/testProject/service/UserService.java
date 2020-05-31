package hu.restumali.testProject.service;

import hu.restumali.testProject.model.UserEntity;
import hu.restumali.testProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity findByUsername(String username) { return userRepository.findOneByUsername(username); }
}
