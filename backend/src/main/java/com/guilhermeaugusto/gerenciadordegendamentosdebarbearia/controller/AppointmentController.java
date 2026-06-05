package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.controller;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto.AppointmentResponse;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto.CreateAppointmentRequest;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Appointment;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service.AppointmentService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public AppointmentResponse createAppointment(@RequestBody CreateAppointmentRequest request) {
        Appointment appointment = appointmentService.createAppointment(
                request.customerId(),
                request.barberId(),
                request.appointmentDate(),
                request.appointmentTime()
        );

        return AppointmentResponse.from(appointment);
    }

    @PatchMapping("/{appointmentId}/cancel")
    public AppointmentResponse cancelAppointment(@PathVariable Long appointmentId) {
        Appointment appointment = appointmentService.cancelAppointment(appointmentId);

        return AppointmentResponse.from(appointment);
    }
}