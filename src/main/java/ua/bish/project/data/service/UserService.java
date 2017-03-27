package ua.bish.project.data.service;

import ua.bish.project.data.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
