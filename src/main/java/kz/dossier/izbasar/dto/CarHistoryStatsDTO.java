package kz.dossier.izbasar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CarHistoryStatsDTO {
    private LocalDate recordDate;
    private Long recordCount;
    private LocalDateTime startTime;
    private LocalDateTime endDate;

    // Constructor
    public CarHistoryStatsDTO(LocalDate recordDate, Long recordCount, LocalDateTime startTime, LocalDateTime endDate) {
        this.recordDate = recordDate;
        this.recordCount = recordCount;
        this.startTime = startTime;
        this.endDate = endDate;
    }

    public LocalDate getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(LocalDate recordDate) {
        this.recordDate = recordDate;
    }

    public Long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Long recordCount) {
        this.recordCount = recordCount;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
