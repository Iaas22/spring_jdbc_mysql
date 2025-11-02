package com.ra2.mysql.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ra2.mysql.model.Customer;
import com.ra2.mysql.repository.CustomerRepository;

@RestController
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //crear 10 customers
    @PostMapping("/api/customer")
    public ResponseEntity<String> addCustomers(@RequestBody Customer customer) {
        return customerRepository.createCustomers(customer);
    }

    //obtener todos los customers
    @GetMapping ("/api/customer")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return customerRepository.getAllCustomers();
    }
   
    //obtener un customer por su id
    @GetMapping("/api/customer/{customer_id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customer_id") int customer_id) {
        return customerRepository.getCustomerById(customer_id);
    }

    //update d eun customer con todos sus datos
    @PutMapping("/api/customer/{customer_id}")
    public ResponseEntity<Customer> updateCustomer (@PathVariable int customer_id, @RequestBody Customer customer){
        return customerRepository.updateCustomer(customer_id, customer);
    }

    //update de un customer solo edad
   @PatchMapping("/api/customer/{customer_id}/age")
    public ResponseEntity<Customer> updateCustomerAge(
        @PathVariable("customer_id") int customerId,
        @RequestParam("age") int age) {

    Customer updatedCustomer = customerRepository.updateCustomerPartial(customerId, age);

    if (updatedCustomer == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(updatedCustomer);
}

    //eliminar un customer por su id
    @DeleteMapping("/api/customer/{customer_id}")
    public String deleteCustomer(@PathVariable("customer_id") int customerId) {
    return customerRepository.deleteCustomer(customerId);
}


    }

