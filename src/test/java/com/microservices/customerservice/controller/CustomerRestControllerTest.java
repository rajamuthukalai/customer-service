package com.microservices.customerservice.controller;

import com.microservices.customerservice.model.Customer;
import com.microservices.customerservice.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerRestController.class)
class CustomerRestControllerTest {

  public static final Long ID = 1L;
  public static final String FIRST_NAME = "Raja";
  public static final String LAST_NAME = "Muthukalai";
  public static final Customer customer = new Customer(ID, FIRST_NAME, LAST_NAME);
  public static final List<Customer> customers =
      Arrays.asList(customer, new Customer(2L, "Sharmila", "Periyaswamy"));
  public static final String CUSTOMER_JSON_STRING =
      "{ \"id\" : \"1\", \"firstName\" : \"test\", \"lastName\" : \"success\"}";

  @Autowired private MockMvc mockMvc;

  @MockBean private CustomerService service;

  @Test
  void getAllCustomers() throws Exception {
    when(service.findAllCustomers()).thenReturn(customers);
    mockMvc
        .perform(MockMvcRequestBuilders.get("/customers"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json(
                    "[{\"id\":1,\"firstName\":\"Raja\",\"lastName\":\"Muthukalai\"},{\"id\":2,\"firstName\":\"Sharmila\",\"lastName\":\"Periyaswamy\"}]"));
  }

  @Test
  void getCustomer() throws Exception {
    when(service.findCustomerById(anyLong())).thenReturn(customer);
    mockMvc
        .perform(MockMvcRequestBuilders.get("/customers/1"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .json("{\"id\":1,\"firstName\":\"Raja\",\"lastName\":\"Muthukalai\"}"));
  }

  @Test
  void addCustomer() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/customers")
                .content(CUSTOMER_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  void updateCustomer() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.put("/customers/1")
                .content(CUSTOMER_JSON_STRING)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  void deleteCustomer() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/customers/1"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
