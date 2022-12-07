package com.example.payheretest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class SwaggerController {
    
    @GetMapping
    public String swagger() {
        return "redirect:/swagger-ui/index.html";
    }
}
