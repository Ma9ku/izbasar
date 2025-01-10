package kz.dossier.izbasar.dto;

import java.util.List;

public class YearAndMonthsAppearanceDto {
    private Integer year;
    private List<Integer> months;

    public YearAndMonthsAppearanceDto(Integer year, List<Integer> months) {
        this.year = year;
        this.months = months;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Integer> getMonths() {
        return months;
    }

    public void setMonths(List<Integer> months) {
        this.months = months;
    }
}
