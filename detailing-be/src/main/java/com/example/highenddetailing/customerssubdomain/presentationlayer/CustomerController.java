package com.example.highenddetailing.customerssubdomain.presentationlayer;

import com.example.highenddetailing.customerssubdomain.businesslayer.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseModel> getCustomerByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok().body(customerService.getCustomerByCustomerId(customerId));
    }
}
