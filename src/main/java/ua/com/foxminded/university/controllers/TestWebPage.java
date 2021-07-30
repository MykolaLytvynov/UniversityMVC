package ua.com.foxminded.university.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestWebPage {

    @GetMapping("/")
    public String getTest() {
        System.out.println("getTest");
        return "test";
    }
}
