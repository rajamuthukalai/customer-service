package com.microservices.customerservice.service;

import com.microservices.customerservice.exception.CustomerNotFoundException;
import com.microservices.customerservice.model.Customer;
import com.microservices.customerservice.respository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
  @Autowired private CustomerRepository repository;

  public List<Customer> findAllCustomers() {
    return (List<Customer>) repository.findAll();
  }

  public Customer findCustomerById(long id) throws CustomerNotFoundException {
    return repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
  }

  public Customer createCustomer(Customer customer) {
    return repository.save(customer);
  }

  public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
    if (repository.existsById(customer.getId())) {
      return repository.save(customer);
    } else {
      throw new CustomerNotFoundException(customer.getId());
    }
  }

  public void deleteCustomerById(long id) throws CustomerNotFoundException {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    } else {
      throw new CustomerNotFoundException(1);
    }
  }
}
