package com.sangeng.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @PreAuthorize("hasAuthority('system:dept:list')")
    @RequestMapping("/hello")
    public String testHello(){
        return"HELLO";
    }
}
