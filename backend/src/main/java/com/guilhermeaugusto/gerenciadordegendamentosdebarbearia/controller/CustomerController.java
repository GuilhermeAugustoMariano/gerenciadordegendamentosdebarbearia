package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.controller;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto.CreateCustomerRequest;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto.CustomerResponse;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Customer;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        Customer customer = customerService.createCustomer(request.name(), request.phone());

        return CustomerResponse.from(customer);
    }
}
