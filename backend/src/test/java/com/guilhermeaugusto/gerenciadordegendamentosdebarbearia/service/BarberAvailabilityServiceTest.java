package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Customer;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.BarberRepository;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class BarberAvailabilityServiceTest {

    @Autowired
    private BarberAvailabilityService availabilityService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void shouldListAvailableTimesInThirtyMinuteSlots() {
        Barber barber = barberRepository.save(new Barber("Carlos"));
        LocalDate friday = LocalDate.of(2026, 6, 5);

        availabilityService.createAvailability(
                barber.getId(),
                DayOfWeek.FRIDAY,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );

        List<LocalTime> availableTimes = availabilityService.findAvailableTimes(barber.getId(), friday);

        assertThat(availableTimes).containsExactly(
                LocalTime.of(9, 0),
                LocalTime.of(9, 30)
        );
    }

    @Test
    void shouldNotListBookedTimeAsAvailable() {
        Barber barber = barberRepository.save(new Barber("Carlos"));
        Customer customer = customerRepository.save(new Customer("Ana", "11999999999"));
        LocalDate friday = LocalDate.of(2026, 6, 5);

        availabilityService.createAvailability(
                barber.getId(),
                DayOfWeek.FRIDAY,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0)
        );
        appointmentService.createAppointment(
                customer.getId(),
                barber.getId(),
                friday,
                LocalTime.of(9, 30)
        );

        List<LocalTime> availableTimes = availabilityService.findAvailableTimes(barber.getId(), friday);

        assertThat(availableTimes).containsExactly(LocalTime.of(9, 0));
    }
}
