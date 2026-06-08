package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.BarberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BarberService {

    private static final Logger logger = LoggerFactory.getLogger(BarberService.class);

    private final BarberRepository barberRepository;

    public BarberService(BarberRepository barberRepository) {
        this.barberRepository = barberRepository;
    }

    @Transactional
    public Barber createBarber(String name) {
        Barber barber = new Barber(name);
        Barber savedBarber = barberRepository.save(barber);

        logger.info("Barber created barberId={}", savedBarber.getId());

        return savedBarber;
    }
}