package com.microservices.customerservice;

import com.microservices.customerservice.controller.CustomerRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomerServiceApplicationTests {

  @Autowired private CustomerRestController controller;

  @Test
  public void contextLoads() throws Exception {
    assertThat(controller).isNotNull();
  }
}
