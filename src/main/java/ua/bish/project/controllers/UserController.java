package ua.bish.project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.bish.project.data.model.User;
import ua.bish.project.data.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "login") String login,
                        @RequestParam(value = "password") String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);

        return "successfully logged in";
    }

    /**'
     * register new user
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam(value = "login") String login,
                           @RequestParam(value = "password") String password) {

        // todo add some validator
        userService.save(new User(login, password));
        return "successfully registered";
    }
}
