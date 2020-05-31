package hu.restumali.testProject.repository;

import hu.restumali.testProject.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public UserEntity findOneByUsername(String username);
}
