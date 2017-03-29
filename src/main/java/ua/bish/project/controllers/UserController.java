package ua.bish.project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.bish.project.data.model.User;
import ua.bish.project.data.service.UserService;
import ua.bish.project.security.jwt.JwtTokenCreationService;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtTokenCreationService jwtTokenCreationService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestParam(value = "login") String login,
                                        @RequestParam(value = "password") String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        if (userDetails != null && bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            String token = jwtTokenCreationService.getToken(userDetails);
            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
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
