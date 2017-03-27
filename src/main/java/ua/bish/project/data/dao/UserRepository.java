package ua.bish.project.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ua.bish.project.data.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
