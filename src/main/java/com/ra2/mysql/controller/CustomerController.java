package com.ra2.mysql.controller;


import java.util.Map;

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
    public String addCustomers() {
        return customerRepository.createCustomers();
    }

    @GetMapping ("/api/customer")
    public String getAllCustomers(){
        return customerRepository.getAllCustomers();
    }
   
    @GetMapping("/api/customer/{id}")
    public String getCustomerById(@PathVariable int id){
        return customerRepository.getCustomerById(id);
    }

    @PutMapping("/api/customer/{id}")
    public String updateCustomer (@PathVariable int id, @RequestBody Customer customer){
        return customerRepository.updateCustomer(id, customer);

    }
    @PatchMapping("/api/customer/{id}")
    public String updateCustomerParcial(@PathVariable int id, @RequestBody Map<String, Object> updates){
          Integer age = (Integer) updates.get("age");
        
          String nombre = (String) updates.get("nombre");

    
          return customerRepository.updateCustomerPartial(id, nombre, age);
    }

    @DeleteMapping("/api/customer/{id}")
    public String deleteCustomer(@PathVariable int id){
        return customerRepository.deleteCustomer(id);
    }

    }

