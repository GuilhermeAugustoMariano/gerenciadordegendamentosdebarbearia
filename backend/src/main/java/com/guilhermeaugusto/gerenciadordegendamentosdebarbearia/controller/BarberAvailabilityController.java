package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.controller;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto.AvailableTimesResponse;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto.BarberAvailabilityResponse;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto.CreateBarberAvailabilityRequest;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.BarberAvailability;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service.BarberAvailabilityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/barbers/{barberId}")
public class BarberAvailabilityController {

    private final BarberAvailabilityService availabilityService;

    public BarberAvailabilityController(BarberAvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping("/availabilities")
    public BarberAvailabilityResponse createAvailability(
            @PathVariable Long barberId,
            @RequestBody CreateBarberAvailabilityRequest request
    ) {
        BarberAvailability availability = availabilityService.createAvailability(
                barberId,
                request.dayOfWeek(),
                request.startTime(),
                request.endTime()
        );

        return BarberAvailabilityResponse.from(availability);
    }

    @GetMapping("/available-times")
    public AvailableTimesResponse findAvailableTimes(
            @PathVariable Long barberId,
            @RequestParam LocalDate date
    ) {
        List<LocalTime> availableTimes = availabilityService.findAvailableTimes(barberId, date);

        return new AvailableTimesResponse(availableTimes);
    }
}
