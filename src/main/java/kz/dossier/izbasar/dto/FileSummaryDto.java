package kz.dossier.izbasar.dto;

import java.time.LocalDateTime;

public class FileSummaryDto {
    private Long id;
    private Boolean status;
    private Integer type;
    private String identifier;
    private Long fixationNumber;
    private String dateFrom;
    private String dateTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getFixationNumber() {
        return fixationNumber;
    }

    public void setFixationNumber(Long fixationNumber) {
        this.fixationNumber = fixationNumber;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
