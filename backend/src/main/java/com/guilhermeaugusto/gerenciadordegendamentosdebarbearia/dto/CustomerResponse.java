package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Customer;

public record CustomerResponse(Long id, String name, String phone) {

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getPhone());
    }
}
