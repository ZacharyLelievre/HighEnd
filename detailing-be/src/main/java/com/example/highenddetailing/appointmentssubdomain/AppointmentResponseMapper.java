package com.example.highenddetailing.appointmentssubdomain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentResponseMapper {
    @Mapping(source = "appointmentId", target = "appointmentId")
    @Mapping(source = "appointmentDate", target = "appointmentDate")
    @Mapping(source = "appointmentTime", target = "appointmentTime")
    @Mapping(source = "serviceId", target = "serviceId")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "employeeId", target = "employeeId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "imagePath", target = "imagePath")
    AppointmentResponseModel entityToResponseModel(Appointment appointment);

    List<AppointmentResponseModel> entityListToResponseModel(List<Appointment> appointments);
}