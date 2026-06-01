package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Appointment;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.AppointmentStatus;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void shouldSaveAppointmentWithCustomerAndBarber() {
        Customer customer = customerRepository.save(new Customer("Ana", "11999999999"));
        Barber barber = barberRepository.save(new Barber("Carlos"));

        Appointment appointment = new Appointment(
                customer,
                barber,
                LocalDate.of(2026, 6, 5),
                LocalTime.of(14, 0)
        );

        Appointment savedAppointment = appointmentRepository.save(appointment);

        assertThat(savedAppointment.getId()).isNotNull();
        assertThat(savedAppointment.getCustomer().getName()).isEqualTo("Ana");
        assertThat(savedAppointment.getBarber().getName()).isEqualTo("Carlos");
        assertThat(savedAppointment.getStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
    }
}
