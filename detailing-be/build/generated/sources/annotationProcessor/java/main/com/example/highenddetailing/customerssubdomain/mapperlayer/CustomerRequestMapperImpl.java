package com.example.highenddetailing.customerssubdomain.mapperlayer;

import com.example.highenddetailing.customerssubdomain.datalayer.Address;
import com.example.highenddetailing.customerssubdomain.datalayer.Customer;
import com.example.highenddetailing.customerssubdomain.datalayer.CustomerIdentifier;
import com.example.highenddetailing.customerssubdomain.presentationlayer.CustomerRequestModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-11T13:31:28-0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.3 (Oracle Corporation)"
)
@Component
public class CustomerRequestMapperImpl implements CustomerRequestMapper {

    @Override
    public Customer requestModelToEntity(CustomerRequestModel customerRequestModel, CustomerIdentifier customerIdentifier, Address address) {
        if ( customerRequestModel == null && customerIdentifier == null && address == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.customerIdentifier( customerIdentifier );
        customer.address( address );

        return customer.build();
    }
}
