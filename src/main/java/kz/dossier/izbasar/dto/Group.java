package kz.dossier.izbasar.dto;

public class Group {
    private String number; // or Integer if it's a number
    private String dateFrom;
    private String dateTo;
    private boolean status;

    // Constructors, getters, and setters

    public Group(String number, String dateFrom, String dateTo, boolean status) {
        this.number = number;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
