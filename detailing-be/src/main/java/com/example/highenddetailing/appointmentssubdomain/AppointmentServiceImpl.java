package com.example.highenddetailing.appointmentssubdomain;

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
}