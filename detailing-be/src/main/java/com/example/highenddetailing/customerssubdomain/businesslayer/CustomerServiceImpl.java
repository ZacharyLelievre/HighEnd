package com.example.highenddetailing.customerssubdomain.businesslayer;


import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerRepository;
import com.example.highenddetailing.customerssubdomain.mapperlayer.CustomerRequestMapper;
import com.example.highenddetailing.customerssubdomain.mapperlayer.CustomerResponseMapper;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerRequestModel;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerResponseMapper customerResponseMapper;
    private final CustomerRequestMapper customerRequestMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerResponseMapper customerResponseMapper, CustomerRequestMapper customerRequestMapper) {
        this.customerRepository = customerRepository;
        this.customerResponseMapper = customerResponseMapper;
        this.customerRequestMapper = customerRequestMapper;
    }

    @Override
    public List<CustomerResponseModel> getCustomers() {

        List<Customer> customers = customerRepository.findAll();
        return customerResponseMapper.entityListToResponseModel(customers);
    }

    @Override
    public void deleteCustomer(String customerId) {
        Customer customer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);
        customerRepository.delete(customer);

    }

    @Override
    public CustomerResponseModel updateCustomer(CustomerRequestModel customerRequestModel, String customerId) {
        Customer customer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);

        Address address = new Address(customerRequestModel.getStreetAddress(), customerRequestModel.getCity(),
                customerRequestModel.getPostalCode(), customerRequestModel.getProvince(), customerRequestModel.getCountry());

        Customer updatedCustomer = customerRequestMapper.requestModelToEntity(customerRequestModel, customer.getCustomerIdentifier(),
                address);

        updatedCustomer.setId(customer.getId());
        Customer response  = customerRepository.save(updatedCustomer);
        return customerResponseMapper.entityToResponseModel(response);

    }

    public CustomerResponseModel getCustomerByCustomerId(String customerId) {
        Customer customer = customerRepository.findCustomerByCustomerIdentifier_CustomerId(customerId);

        return customerResponseMapper.entityToResponseModel(customer);
    }
}
