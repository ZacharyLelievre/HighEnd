package com.example.highenddetailing.appointmentssubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findByAppointmentIdentifier_AppointmentId(String appointmentId);

}
