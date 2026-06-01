package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
