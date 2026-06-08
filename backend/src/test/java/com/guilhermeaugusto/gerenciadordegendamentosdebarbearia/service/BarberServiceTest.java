package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.service;

import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.model.Barber;
import com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.repository.BarberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BarberServiceTest {

    @Mock
    private BarberRepository barberRepository;

    @InjectMocks
    private BarberService barberService;

    @Test
    void shouldCreateBarber() {
        when(barberRepository.save(any(Barber.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Barber barber = barberService.createBarber("Carlos");

        assertThat(barber.getName()).isEqualTo("Carlos");
        verify(barberRepository).save(any(Barber.class));
    }
}