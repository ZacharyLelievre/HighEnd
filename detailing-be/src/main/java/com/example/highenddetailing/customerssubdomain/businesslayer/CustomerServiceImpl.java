package com.example.highenddetailing.customerssubdomain.businesslayer;


import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
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
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(customerId);
        customerRepository.delete(customer);

    }

    @Override
    public CustomerResponseModel updateCustomer(CustomerRequestModel customerRequestModel, String customerId) {
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(customerId);

        Address address = new Address(customerRequestModel.getStreetAddress(), customerRequestModel.getCity(),
                customerRequestModel.getPostalCode(), customerRequestModel.getProvince(), customerRequestModel.getCountry());

        Customer updatedCustomer = customerRequestMapper.requestModelToEntity(customerRequestModel, customer.getCustomerIdentifier(),
                address);

        updatedCustomer.setId(customer.getId());
        Customer response  = customerRepository.save(updatedCustomer);
        return customerResponseMapper.entityToResponseModel(response);

    }
    @Override
    public CustomerResponseModel getCurrentCustomer(String auth0UserId) {
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(auth0UserId);
        if (customer != null) {
            return customerResponseMapper.entityToResponseModel(customer);
        } else {
            return null;
        }
    }
    @Override
    public CustomerResponseModel getCustomerById(String customerId) {
        Customer customer = customerRepository.findByCustomerIdentifier_CustomerId(customerId);
        return (customer != null) ? customerResponseMapper.entityToResponseModel(customer) : null;
    }

    @Override
    public CustomerResponseModel createCustomer(CustomerRequestModel customerRequestModel, String auth0UserId) {
        // Check if customer already exists
        Customer existingCustomer = customerRepository.findByCustomerIdentifier_CustomerId(auth0UserId);
        if (existingCustomer != null) {
            return customerResponseMapper.entityToResponseModel(existingCustomer);
        }

        Address address = new Address(
                customerRequestModel.getStreetAddress(),
                customerRequestModel.getCity(),
                customerRequestModel.getPostalCode(),
                customerRequestModel.getProvince(),
                customerRequestModel.getCountry()
        );

        CustomerIdentifier customerIdentifier = new CustomerIdentifier(auth0UserId);

        Customer newCustomer = Customer.builder()
                .customerIdentifier(customerIdentifier)
                .customerFirstName(customerRequestModel.getCustomerFirstName())
                .customerLastName(customerRequestModel.getCustomerLastName())
                .customerEmailAddress(customerRequestModel.getCustomerEmailAddress())
                .address(address)
                .build();

        Customer savedCustomer = customerRepository.save(newCustomer);
        return customerResponseMapper.entityToResponseModel(savedCustomer);
    }

//    @Override
//    public CustomerResponseModel createCustomer(CustomerRequestModel customerRequestModel, String auth0UserId) {
//        // Check if customer already exists
//        Customer existingCustomer = customerRepository.findByCustomerIdentifier_CustomerId(auth0UserId);
//        if (existingCustomer != null) {
//            return customerResponseMapper.entityToResponseModel(existingCustomer);
//        }
//
//        Address address = new Address(
//                customerRequestModel.getStreetAddress(),
//                customerRequestModel.getCity(),
//                customerRequestModel.getPostalCode(),
//                customerRequestModel.getProvince(),
//                customerRequestModel.getCountry()
//        );
//
//        CustomerIdentifier customerIdentifier = new CustomerIdentifier(auth0UserId);
//
//        Customer newCustomer = Customer.builder()
//                .customerIdentifier(customerIdentifier)
//                .customerFirstName(customerRequestModel.getCustomerFirstName())
//                .customerLastName(customerRequestModel.getCustomerLastName())
//                .customerEmailAddress(customerRequestModel.getCustomerEmailAddress())
//                .address(address)
//                .build();
//
//        Customer savedCustomer = customerRepository.save(newCustomer);
//        return customerResponseMapper.entityToResponseModel(savedCustomer);
//    }

}
