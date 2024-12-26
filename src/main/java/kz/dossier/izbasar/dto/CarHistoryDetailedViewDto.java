package kz.dossier.izbasar.dto;

import kz.dossier.izbasar.model.CarHistory;

import java.util.List;

public class CarHistoryDetailedViewDto {
    private CarHistoryDayStatsDto carHistoryDayStatsDtos;
    private List<CarHistory> carHistory;

    public CarHistoryDayStatsDto getCarHistoryDayStatsDtos() {
        return carHistoryDayStatsDtos;
    }

    public void setCarHistoryDayStatsDtos(CarHistoryDayStatsDto carHistoryDayStatsDtos) {
        this.carHistoryDayStatsDtos = carHistoryDayStatsDtos;
    }

    public List<CarHistory> getCarHistory() {
        return carHistory;
    }

    public void setCarHistory(List<CarHistory> carHistory) {
        this.carHistory = carHistory;
    }
}
