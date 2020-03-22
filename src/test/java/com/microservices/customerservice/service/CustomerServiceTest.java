package com.microservices.customerservice.service;

import com.microservices.customerservice.exception.CustomerNotFoundException;
import com.microservices.customerservice.model.Customer;
import com.microservices.customerservice.respository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

  public static final String FIRST_NAME = "Raja";
  public static final String LAST_NAME = "Muthukalai";
  Customer CUSTOMER = new Customer(1L, FIRST_NAME, LAST_NAME);

  @Mock private CustomerRepository repository;

  @Mock private Customer mockCustomer;

  @InjectMocks private CustomerService service;

  @Test
  public void findAllCustomers() {
    when(repository.findAll()).thenReturn(Arrays.asList(new Customer(1L, FIRST_NAME, LAST_NAME)));
    List<Customer> customers = service.findAllCustomers();
    verify(repository).findAll();
    assertEquals(1, customers.size());
  }

  @Test
  public void findAllCustomers_EmptyList() {
    when(repository.findAll()).thenReturn(new ArrayList<Customer>());
    List<Customer> customers = service.findAllCustomers();
    verify(repository).findAll();
    assertEquals(0, customers.size());
  }

  @Test
  public void findCustomerById() throws CustomerNotFoundException {
    when(repository.findById(anyLong())).thenReturn(Optional.of(CUSTOMER));
    Customer expectedCustomer = service.findCustomerById(1L);
    verify(repository).findById(1L);
    assertEquals(1, expectedCustomer.getId());
    assertEquals(FIRST_NAME, expectedCustomer.getFirstName());
    assertEquals(LAST_NAME, expectedCustomer.getLastName());
  }

  @Test(expected = CustomerNotFoundException.class)
  public void findCustomerById_ThrowCustomerNotFoundException() throws CustomerNotFoundException {
    when(repository.findById(anyLong())).thenThrow(new CustomerNotFoundException(1L));
    service.findCustomerById(1L);
    verify(repository).findById(1L);
  }

  @Test
  public void createCustomer() {
    when(repository.save(any(Customer.class))).thenReturn(CUSTOMER);
    Customer actualCustomer = service.createCustomer(new Customer());
    verify(repository).save(any(Customer.class));
    assertEquals(1, actualCustomer.getId());
    assertEquals(FIRST_NAME, actualCustomer.getFirstName());
    assertEquals(LAST_NAME, actualCustomer.getLastName());
  }

  @Test
  public void updateCustomer() throws CustomerNotFoundException {
    when(mockCustomer.getId()).thenReturn(1L);
    when(repository.existsById(anyLong())).thenReturn(true);
    when(repository.save(any(Customer.class))).thenReturn(CUSTOMER);
    Customer actualCustomer = service.updateCustomer(mockCustomer);
    verify(repository).existsById(mockCustomer.getId());
    verify(repository).save(any(Customer.class));
    assertEquals(1, actualCustomer.getId());
    assertEquals(FIRST_NAME, actualCustomer.getFirstName());
    assertEquals(LAST_NAME, actualCustomer.getLastName());
  }

  @Test(expected = CustomerNotFoundException.class)
  public void updateCustomer_ThrowCustomerNotFoundException() throws CustomerNotFoundException {
    when(mockCustomer.getId()).thenReturn(1L);
    when(repository.existsById(anyLong())).thenReturn(false);
    service.updateCustomer(mockCustomer);
    verify(repository).existsById(mockCustomer.getId());
  }

  @Test
  public void deleteCustomerById() throws CustomerNotFoundException {
    when(mockCustomer.getId()).thenReturn(1L);
    when(repository.existsById(anyLong())).thenReturn(true);
    service.deleteCustomerById(mockCustomer.getId());
    verify(repository).existsById(mockCustomer.getId());
    verify(repository).deleteById(mockCustomer.getId());
  }

  @Test(expected = CustomerNotFoundException.class)
  public void deleteCustomerById_ThrowsCustomerNotFoundException()
      throws CustomerNotFoundException {
    when(mockCustomer.getId()).thenReturn(1L);
    when(repository.existsById(anyLong())).thenReturn(false);
    service.deleteCustomerById(mockCustomer.getId());
    verify(repository).existsById(mockCustomer.getId());
    verify(repository, never()).deleteById(mockCustomer.getId());
  }
}
