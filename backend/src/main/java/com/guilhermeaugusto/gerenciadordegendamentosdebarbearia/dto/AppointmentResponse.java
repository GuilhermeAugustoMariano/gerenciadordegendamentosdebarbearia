package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Appointment;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponse(
        Long id,
        Long customerId,
        Long barberId,
        LocalDate appointmentDate,
        LocalTime appointmentTime,
        AppointmentStatus status
) {

    public static AppointmentResponse from(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getCustomer().getId(),
                appointment.getBarber().getId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime(),
                appointment.getStatus()
        );
    }
}
