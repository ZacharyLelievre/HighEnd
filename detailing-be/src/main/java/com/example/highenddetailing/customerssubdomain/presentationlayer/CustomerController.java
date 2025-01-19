package com.example.highenddetailing.customerssubdomain.presentationlayer;

import com.example.highenddetailing.customerssubdomain.businesslayer.CustomerService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin("http://localhost:3000")
public class CustomerController {

    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<CustomerResponseModel>> getCustomers(){
        return ResponseEntity.ok(customerService.getCustomers());

    }
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseModel> getCustomerById(@PathVariable String customerId) {
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseModel> updateCustomer(@RequestBody CustomerRequestModel customerRequestModel,
                                                                @PathVariable String customerId){
        return ResponseEntity.ok().body(customerService.updateCustomer(customerRequestModel, customerId));
    }


    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();

    }
    @GetMapping("/me")
    public ResponseEntity<CustomerResponseModel> getCurrentCustomer(@AuthenticationPrincipal Jwt jwt) {
        String auth0UserId = jwt.getSubject();
        CustomerResponseModel customer = customerService.getCustomerById(auth0UserId);
        return (customer != null) ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomerResponseModel> createCustomer(@RequestBody CustomerRequestModel customerRequestModel) {
        String auth0UserId = customerRequestModel.getAuth0Sub();

        if (auth0UserId == null || auth0UserId.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            CustomerResponseModel customerResponse = customerService.createCustomer(customerRequestModel, auth0UserId);
            return ResponseEntity.status(201).body(customerResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }



}
