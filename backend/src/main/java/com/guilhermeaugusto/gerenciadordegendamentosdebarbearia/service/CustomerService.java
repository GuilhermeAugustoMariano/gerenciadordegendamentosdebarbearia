package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Customer;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createCustomer(String name, String phone) {
        Customer customer = new Customer(name, phone);

        return customerRepository.save(customer);
    }
}
