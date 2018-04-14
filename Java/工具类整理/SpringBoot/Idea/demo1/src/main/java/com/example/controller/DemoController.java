package com.example.controller;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Flower on 2017/5/12.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/demo1")
public class DemoController {
    @Autowired
    UserService userService;
    @RequestMapping(value="/demo",method = RequestMethod.GET)
    public String index(Model model){
        return "This means deploy right";
    }

    @RequestMapping(value="/Test",method = RequestMethod.GET)
    public String GetTestword(){
        return userService.GetTestword();
    }

}
