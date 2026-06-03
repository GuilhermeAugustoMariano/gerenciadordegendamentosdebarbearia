package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.BarberAvailability;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record BarberAvailabilityResponse(
        Long id,
        Long barberId,
        DayOfWeek dayOfWeek,
        LocalTime startTime,
        LocalTime endTime
) {

    public static BarberAvailabilityResponse from(BarberAvailability availability) {
        return new BarberAvailabilityResponse(
                availability.getId(),
                availability.getBarber().getId(),
                availability.getDayOfWeek(),
                availability.getStartTime(),
                availability.getEndTime()
        );
    }
}
