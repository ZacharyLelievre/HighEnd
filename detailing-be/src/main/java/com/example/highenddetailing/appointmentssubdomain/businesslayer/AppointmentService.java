package com.example.highenddetailing.appointmentssubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;

import java.util.List;

public interface AppointmentService {
    List<AppointmentResponseModel> getAllAppointments();
}
