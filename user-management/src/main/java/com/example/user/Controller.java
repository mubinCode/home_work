package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private MyService service;

    @GetMapping("/home")
    public String home(){
        return service.home();
    }
    @PostMapping("/add")
    public String add(@RequestBody User user){
        return service.add(user);
    }
    @PostMapping("/login")
    public String signIn(@RequestBody User user){
        return service.signIn(user);
    }
}
