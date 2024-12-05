package com.example.highenddetailing.servicessubdomain.datalayer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ServiceRepositoryIntegrationTest {

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    void whenFindAll_thenReturnAllServices() {
        // Arrange: Prepare sample services
        Service service1 = Service.builder()
                .serviceIdentifier(new ServiceIdentifier())
                .serviceName("Test Service 1")
                .timeRequired("1 Hour")
                .price(100.0f)
                .build();

        Service service2 = Service.builder()
                .serviceIdentifier(new ServiceIdentifier())
                .serviceName("Test Service 2")
                .timeRequired("2 Hours")
                .price(200.0f)
                .build();

        serviceRepository.save(service1);
        serviceRepository.save(service2);

        List<Service> services = serviceRepository.findAll();

        // Assert: Verify the results
        assertNotNull(services);
        assertEquals(services.size(), 2);

        assertEquals(service1.getServiceName(), services.get(0).getServiceName());
        assertEquals(service1.getTimeRequired(), services.get(0).getTimeRequired());
        assertEquals(service1.getPrice(), services.get(0).getPrice());

        assertEquals(service2.getServiceName(), services.get(1).getServiceName());
        assertEquals(service2.getTimeRequired(), services.get(1).getTimeRequired());
        assertEquals(service2.getPrice(), services.get(1).getPrice());

    }

}