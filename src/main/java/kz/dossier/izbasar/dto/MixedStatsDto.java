package kz.dossier.izbasar.dto;

import java.util.List;

public class MixedStatsDto {
    private List<CarHistoryStatsDTO> car;

    private List<CarHistoryStatsDTO> phone;
    private List<CarHistoryStatsDTO> mix;

    public List<CarHistoryStatsDTO> getCar() {
        return car;
    }

    public void setCar(List<CarHistoryStatsDTO> car) {
        this.car = car;
    }

    public List<CarHistoryStatsDTO> getPhone() {
        return phone;
    }

    public void setPhone(List<CarHistoryStatsDTO> phone) {
        this.phone = phone;
    }

    public List<CarHistoryStatsDTO> getMix() {
        return mix;
    }

    public void setMix(List<CarHistoryStatsDTO> mix) {
        this.mix = mix;
    }
}
