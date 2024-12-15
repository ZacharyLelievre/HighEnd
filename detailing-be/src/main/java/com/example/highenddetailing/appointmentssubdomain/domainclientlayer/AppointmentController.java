package com.example.highenddetailing.appointmentssubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentService;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{id}/status")
    public ResponseEntity<Appointment> updateAppointmentStatus(@PathVariable String id,
                                                               @RequestBody StatusRequest request) {
        // Convert the incoming string to an enum
        Status newStatus = Status.valueOf(request.getStatus().toUpperCase());
        Appointment updatedAppointment = appointmentService.updateStatus(id, newStatus);
        return ResponseEntity.ok(updatedAppointment);
    }
    @PutMapping("/{id}/assign")
    public ResponseEntity<AppointmentResponseModel> assignEmployee(@PathVariable String id,
                                                                   @RequestBody EmployeeRequestModel request) {
        try {
            AppointmentResponseModel updatedAppointment = appointmentService.assignEmployee(id, request);
            return ResponseEntity.ok(updatedAppointment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}