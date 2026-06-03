package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Appointment;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.AppointmentStatus;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.BarberAvailability;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.AppointmentRepository;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.BarberAvailabilityRepository;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.BarberRepository;
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
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Horario inicial deve ser antes do horario final.");
        }

        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new IllegalArgumentException("Barbeiro nao encontrado."));

        BarberAvailability availability = new BarberAvailability(barber, dayOfWeek, startTime, endTime);

        return availabilityRepository.save(availability);
    }

    public List<LocalTime> findAvailableTimes(Long barberId, LocalDate appointmentDate) {
        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        List<BarberAvailability> availabilities = availabilityRepository.findByBarberIdAndDayOfWeek(barberId, dayOfWeek);
        Set<LocalTime> bookedTimes = findBookedTimes(barberId, appointmentDate);

        return availabilities.stream()
                .flatMap(availability -> buildSlots(availability).stream())
                .filter(slot -> !bookedTimes.contains(slot))
                .distinct()
                .sorted()
                .toList();
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
