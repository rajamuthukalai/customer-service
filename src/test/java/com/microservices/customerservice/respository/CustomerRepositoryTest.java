package com.microservices.customerservice.respository;

import com.microservices.customerservice.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

  public static final String FIRST_NAME = "Raja";
  public static final String LAST_NAME = "Muthukalai";

  @Autowired private CustomerRepository customerRepository;

  @Test
  public void testFindAll() {
    List<Customer> customers = (List<Customer>) customerRepository.findAll();
    assertEquals(1, customers.size()); // check data.sql for the number of customers
  }

  @Test
  public void testFindById() {
    Optional<Customer> customer = customerRepository.findById(1L);
    assertEquals(1, customer.get().getId());
    assertEquals(FIRST_NAME, customer.get().getFirstName());
    assertEquals(LAST_NAME, customer.get().getLastName());
  }

  @Test
  public void testCustomerNotFound() {
    Optional<Customer> customer = customerRepository.findById(100L);
    assertFalse(customer.isPresent());
  }
}
