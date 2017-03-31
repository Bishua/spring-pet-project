package ua.bish.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/rest")
public class RestController {
    @ResponseBody
    @RequestMapping(value = "/test/", method = RequestMethod.GET)
    public String test() {
        return "allowed";
    }
}
