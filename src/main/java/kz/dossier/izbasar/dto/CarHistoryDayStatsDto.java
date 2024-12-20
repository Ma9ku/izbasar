package kz.dossier.izbasar.dto;

import java.time.LocalDateTime;

public class CarHistoryDayStatsDto {
    private LocalDateTime firstDate;
    private LocalDateTime lastDate;
    private Integer countBetween;
    private Integer minutes;

    public CarHistoryDayStatsDto(LocalDateTime firstDate, LocalDateTime lastDate, Integer countBetween, Integer minutes) {
        this.firstDate = firstDate;
        this.lastDate = lastDate;
        this.countBetween = countBetween;
        this.minutes = minutes;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public LocalDateTime getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(LocalDateTime firstDate) {
        this.firstDate = firstDate;
    }

    public LocalDateTime getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDateTime lastDate) {
        this.lastDate = lastDate;
    }

    public Integer getCountBetween() {
        return countBetween;
    }

    public void setCountBetween(Integer countBetween) {
        this.countBetween = countBetween;
    }
}
