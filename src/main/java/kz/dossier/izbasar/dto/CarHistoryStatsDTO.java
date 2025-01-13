package kz.dossier.izbasar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CarHistoryStatsDTO {
    private LocalDate dateLocal;
    private String date;
    private Long fixCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String timeStart;
    private String timeEnd;
    private String type;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CarHistoryStatsDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Constructor

    public CarHistoryStatsDTO(LocalDate recordDate, Long recordCount, LocalDateTime startTime, LocalDateTime endDate) {
        this.dateLocal = recordDate;
        this.fixCount = recordCount;
        this.startDate = startTime;
        this.endDate = endDate;
        this.type = "car";

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.timeStart = (startTime != null) ? startTime.toLocalTime().format(timeFormatter) : null;
        this.timeEnd = (endDate != null) ? endDate.toLocalTime().format(timeFormatter) : null;

        DateTimeFormatter russianDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", Locale.forLanguageTag("ru"));
        this.date = (recordDate != null) ? recordDate.format(russianDateFormatter) : null;
    }
    public CarHistoryStatsDTO(LocalDate recordDate, Long recordCount, LocalDateTime startTime, LocalDateTime endDate, String type) {
        this.dateLocal = recordDate;
        this.fixCount = recordCount;
        this.startDate = startTime;
        this.endDate = endDate;
        this.type = type;

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.timeStart = (startTime != null) ? startTime.toLocalTime().format(timeFormatter) : null;
        this.timeEnd = (endDate != null) ? endDate.toLocalTime().format(timeFormatter) : null;

        DateTimeFormatter russianDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", Locale.forLanguageTag("ru"));
        this.date = (recordDate != null) ? recordDate.format(russianDateFormatter) : null;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public LocalDate getDateLocal() {
        return dateLocal;
    }

    public void setDateLocal(LocalDate dateLocal) {
        this.dateLocal = dateLocal;
    }

    public Long getFixCount() {
        return fixCount;
    }

    public void setFixCount(Long fixCount) {
        this.fixCount = fixCount;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
