package com.example.highenddetailing.appointmentssubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.employeessubdomain.presentationlayer.EmployeeRequestModel;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponseModel> getAllAppointments();
    Appointment updateStatus(String id, Status newStatus);
    AppointmentResponseModel assignEmployee(String id, EmployeeRequestModel request);
}
