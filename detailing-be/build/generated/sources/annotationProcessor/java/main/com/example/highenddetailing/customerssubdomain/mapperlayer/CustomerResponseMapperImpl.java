package com.example.highenddetailing.customerssubdomain.mapperlayer;

import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerResponseModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-11T13:31:28-0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.3 (Oracle Corporation)"
)
@Component
public class CustomerResponseMapperImpl implements CustomerResponseMapper {

    @Override
    public CustomerResponseModel entityToResponseModel(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerResponseModel.CustomerResponseModelBuilder customerResponseModel = CustomerResponseModel.builder();

        customerResponseModel.customerFirstName( customer.getCustomerFirstName() );
        customerResponseModel.customerLastName( customer.getCustomerLastName() );
        customerResponseModel.customerEmailAddress( customer.getCustomerEmailAddress() );

        customerResponseModel.customerId( customer.getCustomerIdentifier().getCustomerId() );
        customerResponseModel.streetAddress( customer.getAddress().getStreetAddress() );
        customerResponseModel.city( customer.getAddress().getCity() );
        customerResponseModel.postalCode( customer.getAddress().getPostalCode() );
        customerResponseModel.province( customer.getAddress().getProvince() );
        customerResponseModel.country( customer.getAddress().getCountry() );

        return customerResponseModel.build();
    }

    @Override
    public List<CustomerResponseModel> entityListToResponseModel(List<Customer> customers) {
        if ( customers == null ) {
            return null;
        }

        List<CustomerResponseModel> list = new ArrayList<CustomerResponseModel>( customers.size() );
        for ( Customer customer : customers ) {
            list.add( entityToResponseModel( customer ) );
        }

        return list;
    }
}
