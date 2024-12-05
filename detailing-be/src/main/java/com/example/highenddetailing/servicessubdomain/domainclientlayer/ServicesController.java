package com.example.highenddetailing.servicessubdomain.domainclientlayer;

import com.example.highenddetailing.servicessubdomain.businesslayer.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/services")
@CrossOrigin(origins = "http://localhost:3000") // Allow specific origin
public class ServicesController {

    private final ServiceService serviceService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ServiceResponseModel>> getAllServices(){
        return ResponseEntity.ok().body(serviceService.getAllServices());
    }
}
