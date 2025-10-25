package com.ra2.mysql.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ra2.mysql.repository.CustomerRepository;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/api/customer")
    public String addCustomers() {
        return customerRepository.createCustomers();
    }
    @GetMapping("/ejemplo")
    public String ejemplo() {
        return customerRepository.testConnection()
        ;
    }

}
