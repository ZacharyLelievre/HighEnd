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
            @Mapping(target = "imagePath", expression = "java(\"/default/path\")")
    })


    Appointment requestModelToEntity(AppointmentRequestModel appointmentRequestModel, AppointmentIdentifier appointmentIdentifier);
}
