package com.example.highenddetailing.appointmentssubdomain.mapperlayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-08T20:21:25-0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class AppointmentResponseMapperImpl implements AppointmentResponseMapper {

    @Override
    public AppointmentResponseModel entityToResponseModel(Appointment appointment) {
        if ( appointment == null ) {
            return null;
        }

        AppointmentResponseModel.AppointmentResponseModelBuilder appointmentResponseModel = AppointmentResponseModel.builder();

        appointmentResponseModel.appointmentId( appointment.getAppointmentIdentifier().getAppointmentId() );
        appointmentResponseModel.appointmentDate( appointment.getAppointmentDate().toString() );
        appointmentResponseModel.appointmentTime( appointment.getAppointmentTime().toString() );
        appointmentResponseModel.serviceId( appointment.getServiceId() );
        appointmentResponseModel.serviceName( appointment.getServiceName() );
        appointmentResponseModel.customerId( appointment.getCustomerId() );
        appointmentResponseModel.customerName( appointment.getCustomerName() );
        appointmentResponseModel.employeeId( appointment.getEmployeeId() );
        appointmentResponseModel.employeeName( appointment.getEmployeeName() );
        appointmentResponseModel.status( appointment.getStatus() );
        appointmentResponseModel.imagePath( appointment.getImagePath() );

        return appointmentResponseModel.build();
    }

    @Override
    public List<AppointmentResponseModel> entityListToResponseModel(List<Appointment> appointments) {
        if ( appointments == null ) {
            return null;
        }

        List<AppointmentResponseModel> list = new ArrayList<AppointmentResponseModel>( appointments.size() );
        for ( Appointment appointment : appointments ) {
            list.add( entityToResponseModel( appointment ) );
        }

        return list;
    }
}
