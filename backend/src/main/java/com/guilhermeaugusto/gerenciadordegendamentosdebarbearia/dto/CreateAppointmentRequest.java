package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateAppointmentRequest(
        Long customerId,
        Long barberId,
        LocalDate appointmentDate,
        LocalTime appointmentTime
) {
}
