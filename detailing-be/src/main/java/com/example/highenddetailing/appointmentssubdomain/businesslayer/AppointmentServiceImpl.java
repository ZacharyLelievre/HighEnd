package com.example.highenddetailing.appointmentssubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentRequestModel;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentRequestMapper;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.utlis.BookingConflictException;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentResponseMapper appointmentResponseMapper;
    private final AppointmentRequestMapper appointmentRequestMapper;
    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;

    public AppointmentServiceImpl(AppointmentResponseMapper appointmentResponseMapper, AppointmentRequestMapper appointmentRequestMapper,
                                  AppointmentRepository appointmentRepository, EmployeeRepository employeeRepository) {
        this.appointmentResponseMapper = appointmentResponseMapper;
        this.appointmentRequestMapper = appointmentRequestMapper;
        this.appointmentRepository = appointmentRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<AppointmentResponseModel> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointmentResponseMapper.entityListToResponseModel(appointments);
    }
    @Override
    public Appointment updateStatus(String id, Status newStatus) {
        // Find by appointmentIdentifier.appointmentId instead of id
        Appointment appointment = appointmentRepository
                .findByAppointmentIdentifier_AppointmentId(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        appointment.setStatus(newStatus);
        return appointmentRepository.save(appointment);
    }
    @Override
    public AppointmentResponseModel assignEmployee(String id, EmployeeRequestModel request) {
        // Check that the appointment exists
        Appointment appointment = appointmentRepository
                .findByAppointmentIdentifier_AppointmentId(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        // Check that the employee exists
        Employee employee = employeeRepository
                .findByEmployeeId(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + request.getEmployeeId()));

        // Assign the employee to the appointment
        appointment.setEmployeeId(employee.getEmployeeId());
        appointment.setEmployeeName(employee.getFirst_name() + " " + employee.getLast_name());

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return appointmentResponseMapper.entityToResponseModel(updatedAppointment);
    }

    //////////////////////////////////////////

    public boolean isTimeSlotAvailable(LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Appointment> overlappingAppointments = appointmentRepository.findOverlappingAppointments(date, startTime, endTime);
        return overlappingAppointments.isEmpty();
    }

    /////////////////////////////////////////

    public AppointmentResponseModel createAppointment(AppointmentRequestModel request) {
        LocalDate date = LocalDate.parse(request.getAppointmentDate());
        LocalTime startTime = LocalTime.parse(request.getAppointmentTime());
        LocalTime endTime = LocalTime.parse(request.getAppointmentEndTime());

        if (!isTimeSlotAvailable(date, startTime, endTime)) {
            throw new BookingConflictException("The time slot is already booked.");
        }

        Appointment appointment = appointmentRequestMapper.requestModelToEntity(request, new AppointmentIdentifier());
        appointment.setStatus(Status.PENDING);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return appointmentResponseMapper.entityToResponseModel(savedAppointment);
    }
    @Override
    public AppointmentResponseModel getAppointmentById(String appointmentId) {
        Appointment appointment = appointmentRepository.findByAppointmentIdentifier_AppointmentId(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + appointmentId));

        return appointmentResponseMapper.entityToResponseModel(appointment);
    }
    @Override
    public void deleteAppointment(String id) {
        Appointment appointment = appointmentRepository
                .findByAppointmentIdentifier_AppointmentId(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        appointmentRepository.delete(appointment);
    }

    @Override
    public AppointmentResponseModel rescheduleAppointment(String id, LocalDate newDate, LocalTime newStartTime, LocalTime newEndTime) {
        // Exclude the current appointment in the overlap check
        if (!appointmentRepository.findOverlappingAppointmentsExcludingCurrent(id, newDate, newStartTime, newEndTime).isEmpty()) {
            throw new BookingConflictException("The new time slot is already booked.");
        }

        // Find the appointment and update it
        Appointment appointment = appointmentRepository
                .findByAppointmentIdentifier_AppointmentId(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        // Set the new values without converting to string
        appointment.setAppointmentDate(newDate);         // LocalDate directly
        appointment.setAppointmentTime(newStartTime);    // LocalTime directly
        appointment.setAppointmentEndTime(newEndTime);   // LocalTime directly

        // Save and return the updated appointment
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return appointmentResponseMapper.entityToResponseModel(updatedAppointment);
    }

    @Override
    public List<AppointmentResponseModel> getAppointmentsByEmployeeId(String employeeId) {
        List<Appointment> appointments = appointmentRepository.findByEmployeeId(employeeId);
        return appointmentResponseMapper.entityListToResponseModel(appointments);
    }
    @Override
    public List<AppointmentResponseModel> getAppointmentsByCustomerId(String customerId) {
        List<Appointment> appointments = appointmentRepository.findByCustomerId(customerId);
        return appointmentResponseMapper.entityListToResponseModel(appointments);
    }

}