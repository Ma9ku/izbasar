package kz.dossier.izbasar.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "mobile_operators")
@Getter
@Setter
public class MobileOperator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_operator", nullable = false)
    private String sourceOperator;

    @Column(name = "isdn_number", nullable = false)
    private String isdnNumber;

    @Column(name = "time_period", nullable = false)
    private LocalDateTime timePeriod;

    @Column(name = "imsi_number")
    private String imsiNumber;

    @Column(name = "record_type")
    private String recordType;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "lac_tac")
    private String lacTac;

    @Column(name = "base_station_type")
    private String baseStationType;

    @Column(name = "azimuth")
    private Double azimuth;

    @Column(name = "width")
    private String width;

    @Column(name = "height")
    private String height;

    @Column(name = "radius")
    private String radius;

    @Column(name = "base_station_location")
    private String baseStationLocation;

    @Column(name = "region")
    private String region;

    @Column(name = "imei")
    private String imei;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceOperator() {
        return sourceOperator;
    }

    public void setSourceOperator(String sourceOperator) {
        this.sourceOperator = sourceOperator;
    }

    public String getIsdnNumber() {
        return isdnNumber;
    }

    public void setIsdnNumber(String isdnNumber) {
        this.isdnNumber = isdnNumber;
    }

    public LocalDateTime getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(LocalDateTime timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getImsiNumber() {
        return imsiNumber;
    }

    public void setImsiNumber(String imsiNumber) {
        this.imsiNumber = imsiNumber;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getLacTac() {
        return lacTac;
    }

    public void setLacTac(String lacTac) {
        this.lacTac = lacTac;
    }

    public String getBaseStationType() {
        return baseStationType;
    }

    public void setBaseStationType(String baseStationType) {
        this.baseStationType = baseStationType;
    }

    public Double getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(Double azimuth) {
        this.azimuth = azimuth;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getBaseStationLocation() {
        return baseStationLocation;
    }

    public void setBaseStationLocation(String baseStationLocation) {
        this.baseStationLocation = baseStationLocation;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
