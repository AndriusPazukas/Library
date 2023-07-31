package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController {

    //@RequestMapping(method = RequestMethod.GET)

    @ResponseBody
    public String hi(){
        return "Hi!";
    }
}
