package com.example.highenddetailing.appointmentssubdomain.utlis;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import org.springframework.beans.BeanUtils;

public class EntityModelUtil {
    public static AppointmentResponseModel toAppointmentResponseModel(Appointment appointment){
        AppointmentResponseModel appointmentResponseModel = new AppointmentResponseModel();
        BeanUtils.copyProperties(appointment, appointmentResponseModel);
        return appointmentResponseModel;
    }
}
