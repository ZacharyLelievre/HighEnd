package com.example.highenddetailing.appointmentssubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.employeessubdomain.datalayer.Employee;
import com.example.highenddetailing.employeessubdomain.datalayer.EmployeeRepository;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentResponseMapper appointmentResponseMapper;
    private final AppointmentRepository appointmentRepository;
    private final EmployeeRepository employeeRepository;

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
                .findByEmployeeIdentifier_EmployeeId(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + request.getEmployeeId()));

        // Assign the employee to the appointment
        appointment.setEmployeeId(employee.getEmployeeIdentifier().getEmployeeId());
        appointment.setEmployeeName(employee.getFirst_name() + " " + employee.getLast_name());

        Appointment updatedAppointment = appointmentRepository.save(appointment);

        return appointmentResponseMapper.entityToResponseModel(updatedAppointment);
    }
}