package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.controller;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto.BarberResponse;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto.CreateBarberRequest;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service.BarberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/barbers")
public class BarberController {

    private final BarberService barberService;

    public BarberController(BarberService barberService) {
        this.barberService = barberService;
    }

    @PostMapping
    public BarberResponse createBarber(@RequestBody CreateBarberRequest request) {
        Barber barber = barberService.createBarber(request.name());

        return BarberResponse.from(barber);
    }
}
