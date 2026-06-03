package com.guilhermeaugusto.gerenciadordegendamentosdebarbearia.dto;

import java.time.LocalTime;
import java.util.List;

public record AvailableTimesResponse(List<LocalTime> availableTimes) {
}
