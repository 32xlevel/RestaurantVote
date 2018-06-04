package ru.s32xlevel.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootContoller {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
