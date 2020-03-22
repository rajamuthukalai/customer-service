package com.microservices.customerservice.controller;

import com.microservices.customerservice.exception.CustomerNotFoundException;
import com.microservices.customerservice.model.Customer;
import com.microservices.customerservice.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class CustomerRestController {

  @Autowired private CustomerService service;

  @GetMapping(value = "/customers")
  public ResponseEntity<List<Customer>> getAllCustomers() {
    return new ResponseEntity<>(service.findAllCustomers(), HttpStatus.OK);
  }

  @GetMapping(value = "/customers/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable long id)
      throws CustomerNotFoundException {
    return new ResponseEntity<>(service.findCustomerById(id), HttpStatus.OK);
  }

  @PostMapping(value = "/customers")
  public ResponseEntity<Customer> addCustomer(
      @Valid @RequestBody Customer customer, UriComponentsBuilder uriComponentsBuilder) {
    try {
      Customer newCustomer = service.createCustomer(customer);
      HttpHeaders headers = new HttpHeaders();
      headers.setLocation(
          uriComponentsBuilder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri());
      return new ResponseEntity<>(newCustomer, headers, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping(value = "/customers/{id}")
  public ResponseEntity<Customer> updateCustomer(
      @PathVariable long id, @RequestBody Customer customer) {
    try {
      return new ResponseEntity<>(service.updateCustomer(customer), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping(value = "/customers/{id}")
  public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable long id)
      throws CustomerNotFoundException {
    service.deleteCustomerById(id);
    return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
  }
}
