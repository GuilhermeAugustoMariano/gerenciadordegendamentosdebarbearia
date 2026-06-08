package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Appointment;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.AppointmentStatus;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Customer;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.AppointmentRepository;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.BarberRepository;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class AppointmentService {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;
    private final BarberRepository barberRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            CustomerRepository customerRepository,
            BarberRepository barberRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.customerRepository = customerRepository;
        this.barberRepository = barberRepository;
    }

    @Transactional
    public Appointment createAppointment(
            Long customerId,
            Long barberId,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    ) {
        logger.info(
                "Creating appointment request customerId={} barberId={} date={} time={}",
                customerId,
                barberId,
                appointmentDate,
                appointmentTime
        );

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente nao encontrado."));

        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new IllegalArgumentException("Barbeiro nao encontrado."));

        boolean barberAlreadyBooked = appointmentRepository.existsByBarberIdAndAppointmentDateAndAppointmentTimeAndStatus(
                barberId,
                appointmentDate,
                appointmentTime,
                AppointmentStatus.SCHEDULED
        );

        if (barberAlreadyBooked) {
            logger.warn(
                    "Appointment rejected because barber is already booked barberId={} date={} time={}",
                    barberId,
                    appointmentDate,
                    appointmentTime
            );
            throw new IllegalArgumentException("Barbeiro ja possui agendamento neste horario.");
        }

        Appointment appointment = new Appointment(customer, barber, appointmentDate, appointmentTime);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        logger.info(
                "Appointment created appointmentId={} customerId={} barberId={} date={} time={}",
                savedAppointment.getId(),
                customerId,
                barberId,
                appointmentDate,
                appointmentTime
        );

        return savedAppointment;
    }

    @Transactional
    public Appointment cancelAppointment(Long appointmentId) {
        logger.info("Canceling appointment request appointmentId={}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Agendamento nao encontrado."));

        appointment.cancel();

        logger.info("Appointment canceled appointmentId={}", appointmentId);

        return appointment;
    }
}