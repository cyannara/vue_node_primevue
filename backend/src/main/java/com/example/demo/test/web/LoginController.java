package com.example.demo.test.web;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.TestService;

@RestController
public class LoginController {

  @Autowired
  TestService testService;

  @GetMapping("/")
  public String main() {
    return "test";
  }

  @GetMapping("/test")
  public String test() {
    return testService.getTime();
  }

}
