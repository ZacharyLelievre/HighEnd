package com.example.highenddetailing.servicessubdomain.domainclientlayer;

import com.example.highenddetailing.servicessubdomain.businesslayer.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "http://localhost:3000") // Allow specific origin
public class ServicesController {

    private final ServiceService serviceService;

    public ServicesController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ServiceResponseModel>> getAllServices(){
        return ResponseEntity.ok().body(serviceService.getAllServices());
    }

    @GetMapping(value = "/{serviceId}", produces = "application/json")
    public ResponseEntity<ServiceResponseModel> getServiceById(@PathVariable String serviceId) {
        return serviceService.getServiceById(serviceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
