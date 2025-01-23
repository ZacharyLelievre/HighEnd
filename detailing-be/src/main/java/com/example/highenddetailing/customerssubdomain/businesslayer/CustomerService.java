package com.example.highenddetailing.customerssubdomain.businesslayer;

import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerRequestModel;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerResponseModel;

import java.util.List;


public interface CustomerService {

    List<CustomerResponseModel> getCustomers();
    void deleteCustomer(String customerId);
    CustomerResponseModel updateCustomer(CustomerRequestModel customerRequestModel, String customerId);
    CustomerResponseModel getCurrentCustomer(String auth0UserId);


    CustomerResponseModel getCustomerById(String customerId);

    CustomerResponseModel createCustomer(CustomerRequestModel customerRequestModel, String auth0UserId);
}
