package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
