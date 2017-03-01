package ua.bish.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String helloWorld() {
        return "hello world";
    }

    @RequestMapping(value = "/other", method = RequestMethod.GET)
    @ResponseBody
    public String helloOtherWorld() {
        return "hello other world";
    }
}
