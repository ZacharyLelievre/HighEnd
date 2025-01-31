package com.example.highenddetailing.appointmentssubdomain.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Optional<Appointment> findByAppointmentIdentifier_AppointmentId(String appointmentId);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate = :date AND (a.appointmentTime < :endTime AND a.appointmentEndTime > :startTime)")
    List<Appointment> findOverlappingAppointments(@Param("date") LocalDate date, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);
    @Query("SELECT a FROM Appointment a WHERE a.appointmentIdentifier.appointmentId != :appointmentId " +
            "AND a.appointmentDate = :date " +
            "AND (a.appointmentTime < :endTime AND a.appointmentEndTime > :startTime)")
    List<Appointment> findOverlappingAppointmentsExcludingCurrent(@Param("appointmentId") String appointmentId,
                                                                  @Param("date") LocalDate date,
                                                                  @Param("startTime") LocalTime startTime,
                                                                  @Param("endTime") LocalTime endTime);
    List<Appointment> findByEmployeeId(String employeeId);


}
