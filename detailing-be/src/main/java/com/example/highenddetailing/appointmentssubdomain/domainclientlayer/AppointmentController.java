package com.example.highenddetailing.appointmentssubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentService;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentResponseMapper appointmentResponseMapper;

    public AppointmentController(AppointmentService appointmentService, AppointmentResponseMapper appointmentResponseMapper) {
        this.appointmentService = appointmentService;
        this.appointmentResponseMapper = appointmentResponseMapper;
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
    public ResponseEntity<AppointmentResponseModel> updateAppointmentStatus(
            @PathVariable String id,
            @RequestBody StatusRequest request
    ) {
        Status newStatus = Status.valueOf(request.getStatus().toUpperCase());
        Appointment updatedAppointment = appointmentService.updateStatus(id, newStatus);
        // Convert the entity to your usual response model with top-level appointmentId
        AppointmentResponseModel updatedResponse = appointmentResponseMapper.entityToResponseModel(updatedAppointment);
        return ResponseEntity.ok(updatedResponse);
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
//    @PutMapping("/{appointmentId}/status")
//    public ResponseEntity<AppointmentResponseModel> updateStatus(
//            @PathVariable String appointmentId,
//            @RequestBody StatusRequest statusRequest
//    ) {
//        Status newStatus = Status.valueOf(statusRequest.getStatus().toUpperCase());
//        var updated = appointmentService.updateStatus(appointmentId, newStatus);
//        return ResponseEntity.ok(AppointmentResponseModel.builder()
//                .appointmentId(updated.getAppointmentIdentifier().getAppointmentId())
//                .appointmentDate(updated.getAppointmentDate().toString())
//                .appointmentTime(updated.getAppointmentTime().toString())
//                .appointmentEndTime(updated.getAppointmentEndTime().toString())
//                .serviceId(updated.getServiceId())
//                .serviceName(updated.getServiceName())
//                .customerId(updated.getCustomerId())
//                .customerName(updated.getCustomerName())
//                .employeeId(updated.getEmployeeId())
//                .employeeName(updated.getEmployeeName())
//                .status(updated.getStatus())
//                .imagePath(updated.getImagePath())
//                .build());
//    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AppointmentResponseModel>> getAppointmentsByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByEmployeeId(employeeId));
    }
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseModel> getAppointmentById(@PathVariable String appointmentId) {
        AppointmentResponseModel appt = appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appt);
    }

}