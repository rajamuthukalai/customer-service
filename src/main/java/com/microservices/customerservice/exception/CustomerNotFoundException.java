package com.microservices.customerservice.exception;

public class CustomerNotFoundException extends RuntimeException {

  public CustomerNotFoundException(long id) {
    super(String.format("Customer with id: %d not found", id));
  }

  public CustomerNotFoundException(String message) {
    super(message);
  }

  public CustomerNotFoundException(Throwable cause) {
    super(cause);
  }

  public CustomerNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
