package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Appointment;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.AppointmentStatus;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.BarberAvailability;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.AppointmentRepository;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.BarberAvailabilityRepository;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.BarberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BarberAvailabilityService {

    private static final Logger logger = LoggerFactory.getLogger(BarberAvailabilityService.class);
    private static final int SLOT_MINUTES = 30;

    private final BarberAvailabilityRepository availabilityRepository;
    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;

    public BarberAvailabilityService(
            BarberAvailabilityRepository availabilityRepository,
            AppointmentRepository appointmentRepository,
            BarberRepository barberRepository
    ) {
        this.availabilityRepository = availabilityRepository;
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
    }

    @Transactional
    public BarberAvailability createAvailability(
            Long barberId,
            DayOfWeek dayOfWeek,
            LocalTime startTime,
            LocalTime endTime
    ) {
        logger.info(
                "Creating barber availability request barberId={} dayOfWeek={} startTime={} endTime={}",
                barberId,
                dayOfWeek,
                startTime,
                endTime
        );

        if (!startTime.isBefore(endTime)) {
            logger.warn(
                    "Availability rejected because start time is not before end time barberId={} startTime={} endTime={}",
                    barberId,
                    startTime,
                    endTime
            );
            throw new IllegalArgumentException("Horario inicial deve ser antes do horario final.");
        }

        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new IllegalArgumentException("Barbeiro nao encontrado."));

        BarberAvailability availability = new BarberAvailability(barber, dayOfWeek, startTime, endTime);
        BarberAvailability savedAvailability = availabilityRepository.save(availability);

        logger.info(
                "Barber availability created availabilityId={} barberId={} dayOfWeek={} startTime={} endTime={}",
                savedAvailability.getId(),
                barberId,
                dayOfWeek,
                startTime,
                endTime
        );

        return savedAvailability;
    }

    public List<LocalTime> findAvailableTimes(Long barberId, LocalDate appointmentDate) {
        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        List<BarberAvailability> availabilities = availabilityRepository.findByBarberIdAndDayOfWeek(barberId, dayOfWeek);
        Set<LocalTime> bookedTimes = findBookedTimes(barberId, appointmentDate);

        List<LocalTime> availableTimes = availabilities.stream()
                .flatMap(availability -> buildSlots(availability).stream())
                .filter(slot -> !bookedTimes.contains(slot))
                .distinct()
                .sorted()
                .toList();

        logger.info(
                "Available times found barberId={} date={} availableCount={} bookedCount={}",
                barberId,
                appointmentDate,
                availableTimes.size(),
                bookedTimes.size()
        );

        return availableTimes;
    }

    private Set<LocalTime> findBookedTimes(Long barberId, LocalDate appointmentDate) {
        List<Appointment> appointments = appointmentRepository.findByBarberIdAndAppointmentDateAndStatus(
                barberId,
                appointmentDate,
                AppointmentStatus.SCHEDULED
        );

        Set<LocalTime> bookedTimes = new HashSet<>();

        for (Appointment appointment : appointments) {
            bookedTimes.add(appointment.getAppointmentTime());
        }

        return bookedTimes;
    }

    private List<LocalTime> buildSlots(BarberAvailability availability) {
        LocalTime currentTime = availability.getStartTime();
        LocalTime endTime = availability.getEndTime();
        List<LocalTime> slots = new java.util.ArrayList<>();

        while (currentTime.isBefore(endTime)) {
            slots.add(currentTime);
            currentTime = currentTime.plusMinutes(SLOT_MINUTES);
        }

        return slots;
    }
}