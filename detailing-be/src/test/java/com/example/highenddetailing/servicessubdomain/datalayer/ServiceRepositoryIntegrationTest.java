package com.example.highenddetailing.servicessubdomain.datalayer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void whenFindByServiceId_thenReturnService() {
        // Arrange: Prepare a sample service
        ServiceIdentifier serviceIdentifier = new ServiceIdentifier();
        Service service = Service.builder()
                .serviceIdentifier(serviceIdentifier)
                .serviceName("Test Service")
                .timeRequired("1 Hour")
                .price(100.0f)
                .build();

        serviceRepository.save(service);

        // Act: Fetch the service by serviceId
        Optional<Service> foundService = serviceRepository.findByServiceIdentifier_ServiceId(serviceIdentifier.getServiceId());

        // Assert: Verify the result
        assertTrue(foundService.isPresent());
        assertEquals(service.getServiceName(), foundService.get().getServiceName());
        assertEquals(service.getTimeRequired(), foundService.get().getTimeRequired());
        assertEquals(service.getPrice(), foundService.get().getPrice());
        assertEquals(serviceIdentifier.getServiceId(), foundService.get().getServiceIdentifier().getServiceId());
    }

}