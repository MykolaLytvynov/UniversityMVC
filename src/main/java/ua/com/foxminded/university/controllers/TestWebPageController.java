package ua.com.foxminded.university.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class TestWebPageController {


    @GetMapping("/")
    public String getHome() {
        log.info("Controller Home called");
        return "home";
    }

    @GetMapping("/mvc")
    public String getMvcTest() {
        log.info("Controller Mvc called");
        return "mvc";
    }

    @GetMapping("/get")
    public String getTwoMwc() {
        log.info("Controller GET called");
        return "gettest";
    }

    @GetMapping("/drei")
    public String getDrei() {
        log.info("Controller Drei called");
        return "drei";
    }
}
