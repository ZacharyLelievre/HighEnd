package com.example.highenddetailing.appointmentssubdomain.mapperlayer;

import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentResponseMapper {
    @Mapping(expression = "java(appointment.getAppointmentIdentifier().getAppointmentId())", target = "appointmentId")
    @Mapping(expression = "java(appointment.getAppointmentDate().toString())", target = "appointmentDate")
    @Mapping(expression = "java(appointment.getAppointmentTime().toString())", target = "appointmentTime")
    @Mapping(expression = "java(appointment.getServiceId().getServiceId())", target = "serviceId")
    @Mapping(expression = "java(appointment.getCustomerId())", target = "customerId")
    @Mapping(expression = "java(appointment.getEmployeeId())", target = "employeeId")
    @Mapping(expression = "java(appointment.getStatus())", target = "status")
    @Mapping(expression = "java(appointment.getImagePath())", target = "imagePath")
    AppointmentResponseModel entityToResponseModel(Appointment appointment);

    List<AppointmentResponseModel> entityListToResponseModel(List<Appointment> appointments);
}