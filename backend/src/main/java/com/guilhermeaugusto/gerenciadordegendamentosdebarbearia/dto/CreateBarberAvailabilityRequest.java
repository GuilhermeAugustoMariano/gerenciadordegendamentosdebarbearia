package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record CreateBarberAvailabilityRequest(
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
) {
}
