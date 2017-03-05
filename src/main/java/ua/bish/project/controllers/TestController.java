package ua.bish.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.bish.project.data.dao.User;
import ua.bish.project.data.repository.UserRepository;

@Controller
public class TestController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "test is successful if you see this message, and userRepository is null: " + (userRepository == null);
    }

    @RequestMapping(value = "/data/user", method = RequestMethod.PUT)
    public void createUser() {
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setPassword("password");
        user.setEmail("email@gmail.com");
        userRepository.create(user);
    }
}
