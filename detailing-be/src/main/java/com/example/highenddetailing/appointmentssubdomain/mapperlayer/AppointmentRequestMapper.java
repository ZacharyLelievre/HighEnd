package com.example.highenddetailing.appointmentssubdomain.mapperlayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentIdentifier;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AppointmentRequestMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "status", constant = "PENDING"),
            @Mapping(target = "imagePath", expression = "java(\"/default/path\")"),
            @Mapping(target = "appointmentTime", expression = "java(java.time.LocalTime.parse(appointmentRequestModel.getAppointmentTime()))"),
            @Mapping(target = "appointmentEndTime", expression = "java(java.time.LocalTime.parse(appointmentRequestModel.getAppointmentEndTime()))"),
            @Mapping(expression = "java(appointmentRequestModel.getEmployeeId() == null || appointmentRequestModel.getEmployeeId().isEmpty() ? \"UNASSIGNED\" : appointmentRequestModel.getEmployeeId())", target = "employeeId"),
            @Mapping(expression = "java(appointmentRequestModel.getEmployeeName() == null || appointmentRequestModel.getEmployeeName().isEmpty() ? \"UNASSIGNED\" : appointmentRequestModel.getEmployeeName())", target = "employeeName")
    })
    Appointment requestModelToEntity(AppointmentRequestModel appointmentRequestModel, AppointmentIdentifier appointmentIdentifier);
}