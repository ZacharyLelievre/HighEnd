package com.example.highenddetailing.appointmentssubdomain.businesslayer;

import com.example.highenddetailing.appointmentssubdomain.datalayer.Appointment;
import com.example.highenddetailing.appointmentssubdomain.datalayer.AppointmentRepository;
import com.example.highenddetailing.appointmentssubdomain.datalayer.Status;
import com.example.highenddetailing.appointmentssubdomain.mapperlayer.AppointmentResponseMapper;
import com.example.highenddetailing.appointmentssubdomain.domainclientlayer.AppointmentResponseModel;
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
}