package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.BarberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BarberService {

    private final BarberRepository barberRepository;

    public BarberService(BarberRepository barberRepository) {
        this.barberRepository = barberRepository;
    }

    @Transactional
    public Barber createBarber(String name) {
        Barber barber = new Barber(name);

        return barberRepository.save(barber);
    }
}
