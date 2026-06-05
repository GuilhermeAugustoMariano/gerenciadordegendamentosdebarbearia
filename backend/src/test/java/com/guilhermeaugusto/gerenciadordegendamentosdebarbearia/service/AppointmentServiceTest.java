package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Appointment;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.AppointmentStatus;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Customer;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.AppointmentRepository;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.BarberRepository;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class AppointmentServiceTest {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BarberRepository barberRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void shouldCreateAppointmentWhenBarberIsAvailable() {
        Customer customer = customerRepository.save(new Customer("Ana", "11999999999"));
        Barber barber = barberRepository.save(new Barber("Carlos"));

        Appointment appointment = appointmentService.createAppointment(
                customer.getId(),
                barber.getId(),
                LocalDate.of(2026, 6, 5),
                LocalTime.of(14, 0)
        );

        assertThat(appointment.getId()).isNotNull();
        assertThat(appointment.getStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
        assertThat(appointmentRepository.count()).isEqualTo(1);
    }

    @Test
    void shouldNotCreateAppointmentWhenBarberIsAlreadyBooked() {
        Customer firstCustomer = customerRepository.save(new Customer("Ana", "11999999999"));
        Customer secondCustomer = customerRepository.save(new Customer("Bruno", "11888888888"));
        Barber barber = barberRepository.save(new Barber("Carlos"));
        LocalDate appointmentDate = LocalDate.of(2026, 6, 5);
        LocalTime appointmentTime = LocalTime.of(14, 0);

        appointmentService.createAppointment(firstCustomer.getId(), barber.getId(), appointmentDate, appointmentTime);

        assertThatThrownBy(() -> appointmentService.createAppointment(
                secondCustomer.getId(),
                barber.getId(),
                appointmentDate,
                appointmentTime
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Barbeiro ja possui agendamento neste horario.");
    }

    @Test
    void shouldCancelAppointment() {
        Customer customer = customerRepository.save(new Customer("Ana", "11999999999"));
        Barber barber = barberRepository.save(new Barber("Carlos"));
        Appointment appointment = appointmentService.createAppointment(
                customer.getId(),
                barber.getId(),
                LocalDate.of(2026, 6, 5),
                LocalTime.of(14, 0)
        );

        Appointment canceledAppointment = appointmentService.cancelAppointment(appointment.getId());

        assertThat(canceledAppointment.getStatus()).isEqualTo(AppointmentStatus.CANCELED);
    }
}