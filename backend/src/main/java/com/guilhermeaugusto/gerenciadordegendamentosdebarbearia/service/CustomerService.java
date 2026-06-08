package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Customer;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createCustomer(String name, String phone) {
        Customer customer = new Customer(name, phone);
        Customer savedCustomer = customerRepository.save(customer);

        logger.info("Customer created customerId={}", savedCustomer.getId());

        return savedCustomer;
    }
}