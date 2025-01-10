package kz.dossier.izbasar.dto;

import jakarta.persistence.Column;

import java.time.LocalDateTime;

public class ExactFixationInfoDto {
    private String coordinates;
    private String address;
    private LocalDateTime date;
    private Integer orderId;
    private String minioImage;

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getMinioImage() {
        return minioImage;
    }

    public void setMinioImage(String minioImage) {
        this.minioImage = minioImage;
    }
}
