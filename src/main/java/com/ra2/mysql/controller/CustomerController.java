package com.ra2.mysql.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ra2.mysql.Services.CustomerService;
import com.ra2.mysql.model.Customer;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    

    //obtener todos
    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(service.getAllCustomers());
    }

    //obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable int id) {
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    //update entero
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateFull(
            @PathVariable int id,
            @RequestBody Customer c) {
        return ResponseEntity.ok(service.updateCustomer(id, c));
    }

    //update solo una cosa
    @PatchMapping("/{id}/age")
    public ResponseEntity<Customer> updateAge(
            @PathVariable int id,
            @RequestParam int age) {
        return ResponseEntity.ok(service.updateCustomerAge(id, age));
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        return ResponseEntity.ok(service.deleteCustomer(id));
    }

    //subir una imagen
    @PostMapping("/users/{user_id}/image")
    public ResponseEntity<String> uploadImage(
        @PathVariable("user_id") Long userId,
        @RequestParam("imageFile") MultipartFile imageFile) {

    return service.uploadUserImage(userId, imageFile);
}

 



}
