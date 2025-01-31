package com.example.highenddetailing.appointmentssubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentService;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.utlis.BookingConflictException;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<AppointmentResponseModel>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<AppointmentResponseModel> createAppointment(@RequestBody AppointmentRequestModel appointmentRequestModel) {
        AppointmentResponseModel response = appointmentService.createAppointment(appointmentRequestModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable String id) {
        try {
            appointmentService.deleteAppointment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AppointmentResponseModel>> getAppointmentsByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByEmployeeId(employeeId));
    }

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<AppointmentResponseModel> rescheduleAppointment(
            @PathVariable String id, @RequestBody RescheduleRequest request) {
        try {
            AppointmentResponseModel updatedAppointment = appointmentService.rescheduleAppointment(
                    id,
                    LocalDate.parse(request.getNewDate()),
                    LocalTime.parse(request.getNewStartTime()),
                    LocalTime.parse(request.getNewEndTime())
            );
            return ResponseEntity.ok(updatedAppointment);
        } catch (BookingConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
}