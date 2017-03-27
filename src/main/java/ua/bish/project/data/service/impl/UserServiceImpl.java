package ua.bish.project.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.bish.project.data.dao.RoleRepository;
import ua.bish.project.data.dao.UserRepository;
import ua.bish.project.data.model.Role;
import ua.bish.project.data.model.User;
import ua.bish.project.data.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();

        Role role = roleRepository.findOne(1L);
        if (role == null) {
            role = new Role(1L, "ROLE_USER");
            roleRepository.save(role);
        }

        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
