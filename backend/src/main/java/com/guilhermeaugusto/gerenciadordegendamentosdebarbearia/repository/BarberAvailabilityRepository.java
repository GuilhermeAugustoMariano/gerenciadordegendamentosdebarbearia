package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.BarberAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface BarberAvailabilityRepository extends JpaRepository<BarberAvailability, Long> {

    List<BarberAvailability> findByBarberIdAndDayOfWeek(Long barberId, DayOfWeek dayOfWeek);
}
