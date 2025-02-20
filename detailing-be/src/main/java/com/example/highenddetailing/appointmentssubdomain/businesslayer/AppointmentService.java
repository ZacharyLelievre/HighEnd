package com.example.highenddetailing.appointmentssubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentRequestModel;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponseModel> getAllAppointments();
    AppointmentResponseModel updateStatus(String id, Status newStatus);

    AppointmentResponseModel assignEmployee(String id, EmployeeRequestModel request);
    AppointmentResponseModel createAppointment(AppointmentRequestModel request);
    void deleteAppointment(String id);  // Add this method
    List<AppointmentResponseModel> getAppointmentsByEmployeeId(String employeeId);

    List<AppointmentResponseModel> getAppointmentsByCustomerId(String customerId);

    AppointmentResponseModel rescheduleAppointment(String id, LocalDate newDate, LocalTime newStartTime, LocalTime newEndTime);
    AppointmentResponseModel getAppointmentById(String appointmentId);



}
