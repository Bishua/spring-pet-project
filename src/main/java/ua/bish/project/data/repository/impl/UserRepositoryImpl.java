package ua.bish.project.data.repository.impl;

import org.springframework.stereotype.Repository;
import ua.bish.project.data.dao.User;
import ua.bish.project.data.repository.UserRepository;
import ua.bish.project.data.repository.common.OperationsImpl;

@Repository("userRepository")
public class UserRepositoryImpl extends OperationsImpl<User, Integer> implements UserRepository {
    public UserRepositoryImpl() {
        setType(User.class);
    }
}