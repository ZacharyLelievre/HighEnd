package com.example.highenddetailing.appointmentssubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<AppointmentResponseModel>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }
}