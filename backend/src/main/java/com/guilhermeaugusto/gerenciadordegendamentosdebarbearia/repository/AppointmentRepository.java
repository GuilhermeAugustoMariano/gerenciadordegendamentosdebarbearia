package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Appointment;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByBarberIdAndAppointmentDateAndAppointmentTimeAndStatus(
            Long barberId,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            AppointmentStatus status
    );

    List<Appointment> findByBarberIdAndAppointmentDateAndStatus(
            Long barberId,
            LocalDate appointmentDate,
            AppointmentStatus status
    );
}
