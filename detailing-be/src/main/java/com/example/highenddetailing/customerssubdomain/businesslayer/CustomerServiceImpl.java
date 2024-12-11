package com.example.highenddetailing.customerssubdomain.businesslayer;


import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerRepository;
import com.example.highenddetailing.customerssubdomain.mapperlayer.CustomerResponseMapper;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerResponseMapper customerResponseMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerResponseMapper customerResponseMapper) {
        this.customerRepository = customerRepository;
        this.customerResponseMapper = customerResponseMapper;
    }

    @Override
    public List<CustomerResponseModel> getCustomers() {

        List<Customer> customers = customerRepository.findAll();
        return customerResponseMapper.entityListToResponseModel(customers);
    }

    @Override
    public void deleteCustomer(String customerId) {
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(customerId);
        customerRepository.delete(customer);

    }

}
