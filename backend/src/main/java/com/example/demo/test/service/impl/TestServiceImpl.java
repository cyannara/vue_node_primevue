package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.service.TestService;

@Service
public class TestServiceImpl implements TestService {

  @Autowired
  TestMapper testMapper;

  @Override
  public String getTime() {
    return testMapper.getTime();
  }

}
