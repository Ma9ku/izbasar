package kz.dossier.izbasar.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FixationGroupDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer fixNumber;
    private String duration;

    private List<ExactFixationInfoDto> fixations;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getFixNumber() {
        return fixNumber;
    }

    public void setFixNumber(Integer fixNumber) {
        this.fixNumber = fixNumber;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<ExactFixationInfoDto> getFixations() {
        return fixations;
    }

    public void setFixations(List<ExactFixationInfoDto> fixations) {
        this.fixations = fixations;
    }
}
