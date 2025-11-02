package com.ra2.mysql.controller;


import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ra2.mysql.model.Customer;
import com.ra2.mysql.repository.CustomerRepository;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PostMapping("/api/customer")
    public ResponseEntity<String> addCustomers(@RequestBody Customer customer) {
        return customerRepository.createCustomers(customer);
    }

    @GetMapping ("/api/customer")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return customerRepository.getAllCustomers();
    }
   
    @GetMapping("/api/customer/{customer_id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int customer_id){
        return customerRepository.getCustomerById(customer_id);
    }

    @PutMapping("/api/customer/{customer_id}")
    public ResponseEntity<Customer> updateCustomer (@PathVariable int customer_id, @RequestBody Customer customer){
        return customerRepository.updateCustomer(customer_id, customer);

    }
    @PatchMapping("/api/customer/{customer_id}")
    public String updateCustomerParcial(@PathVariable int id, @RequestBody Map<String, Object> updates){
          Integer age = (Integer) updates.get("age");
        
    
          return customerRepository.updateCustomerPartial(id, age);
    }

    @DeleteMapping("/api/customer/{id}")
    public String deleteCustomer(@PathVariable int id){
        return customerRepository.deleteCustomer(id);
    }

    }

