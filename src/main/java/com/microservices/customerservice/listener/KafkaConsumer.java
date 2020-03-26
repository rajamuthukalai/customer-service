package com.microservices.customerservice.listener;

import com.microservices.customerservice.model.Customer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

  @KafkaListener(topics = "customer", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
  public void kafkaConsumer(Customer customer) {
    System.out.println(customer.getFirstName());
  }
}
