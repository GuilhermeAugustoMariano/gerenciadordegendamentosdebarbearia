package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<Barber, Long> {
}
