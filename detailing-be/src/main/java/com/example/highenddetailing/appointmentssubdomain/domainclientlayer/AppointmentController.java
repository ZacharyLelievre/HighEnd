package com.example.highenddetailing.appointmentssubdomain.domainclientlayer;

import com.example.highenddetailing.appointmentssubdomain.businesslayer.AppointmentService;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.utlis.BookingConflictException;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
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
    private final AppointmentResponseMapper appointmentResponseMapper;

    private final AppointmentRepository appointmentRepository;

    public AppointmentController(AppointmentService appointmentService, AppointmentResponseMapper appointmentResponseMapper, AppointmentRepository appointmentRepository) {
        this.appointmentService = appointmentService;
        this.appointmentResponseMapper = appointmentResponseMapper;
        this.appointmentRepository = appointmentRepository;
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

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AppointmentResponseModel>> getAppointmentsByCustomerId(@PathVariable String customerId) {
        List<AppointmentResponseModel> appointments = appointmentService.getAppointmentsByCustomerId(customerId);
        return ResponseEntity.ok(appointments);
    }

        @PutMapping("/{id}/reschedule")
        public ResponseEntity<AppointmentResponseModel> rescheduleAppointment (
                @PathVariable String id, @RequestBody RescheduleRequest request){
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

        @GetMapping("/{appointmentId}")
        public ResponseEntity<AppointmentResponseModel> getAppointmentById (@PathVariable String appointmentId){
            AppointmentResponseModel appt = appointmentService.getAppointmentById(appointmentId);
            return ResponseEntity.ok(appt);
        }
    @GetMapping("/date-appointments")
    public ResponseEntity<List<AppointmentResponseModel>> getAppointmentsByDate(@RequestParam("date") String dateStr) {
        LocalDate date = LocalDate.parse(dateStr);
        List<Appointment> appointments = appointmentRepository.findByAppointmentDate(date);
        List<AppointmentResponseModel> response = appointmentResponseMapper.entityListToResponseModel(appointments);
        return ResponseEntity.ok(response);
    }
}