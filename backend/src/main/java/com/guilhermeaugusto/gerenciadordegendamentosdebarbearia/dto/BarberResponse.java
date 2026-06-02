package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;

public record BarberResponse(Long id, String name) {

    public static BarberResponse from(Barber barber) {
        return new BarberResponse(barber.getId(), barber.getName());
    }
}
