package kz.dossier.izbasar.service;

import kz.dossier.izbasar.dto.CarHistoryDayStatsDto;
import kz.dossier.izbasar.dto.CarHistoryDetailedViewDto;
import kz.dossier.izbasar.dto.CarHistoryStatsDTO;
import kz.dossier.izbasar.model.CarHistory;
import kz.dossier.izbasar.repository.CarHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarHistoryService {

    @Autowired
    private CarHistoryRepository repository;

    public List<CarHistory> getAllCarHistories() {
        return repository.findAll();
    }
    public List<CarHistoryStatsDTO> getCarHistoryStatistics(String plate_number) {
        List<Object[]> results = repository.getCarHistoryStats(plate_number);
        return results.stream().map(row -> new CarHistoryStatsDTO(
                ((java.sql.Date) row[0]).toLocalDate(),          // record_date
                ((Number) row[1]).longValue(), // record_count
                ((java.sql.Timestamp) row[2]).toLocalDateTime(),          // record_date
                ((java.sql.Timestamp) row[3]).toLocalDateTime()          // record_date
        )).collect(Collectors.toList());
    }

    public List<CarHistory> getCarHistoryBetweenDate(String startDate, String endDate, String plate_number){
        return repository.findByDateBetweenAndPlateNumber(LocalDateTime.parse(startDate),LocalDateTime.parse(endDate),plate_number);
    }

    public Page<CarHistory> getCarsPage(Pageable pageable){
        return repository.getAllPage(pageable);
    }

    public List<CarHistoryDayStatsDto> getCarHistoryDatStats(String date){
        List<Object[]> results = repository.getCarHistoryDay(LocalDate.parse(date));

        return results.stream().map(row -> new CarHistoryDayStatsDto(
                ((java.sql.Timestamp) row[0]).toLocalDateTime(),
                ((java.sql.Timestamp) row[1]).toLocalDateTime(),
                ((Number) row[2]).intValue(),
                ((Number) row[3]).intValue()
        )).collect(Collectors.toList());
    }

    public List<CarHistoryDetailedViewDto> carHistoryDetailedViewDtos(String startDate, String endDate, String plateNumber, Integer interval){
        List<Object[]> results = repository.getCarHistoryDay(LocalDateTime.parse(startDate), LocalDateTime.parse(endDate), plateNumber, interval);
        List<CarHistoryDetailedViewDto> carHistoryDetailedViewDtos = new ArrayList<>();
        List<CarHistoryDayStatsDto> carHistoryDayStatsDtos = results.stream().map(row -> new CarHistoryDayStatsDto(
                ((java.sql.Timestamp) row[0]).toLocalDateTime(),
                ((java.sql.Timestamp) row[1]).toLocalDateTime(),
                ((Number) row[2]).intValue(),
                ((Number) row[3]).intValue()
        )).collect(Collectors.toList());

        for(CarHistoryDayStatsDto carHistoryDayStatsDto: carHistoryDayStatsDtos){
            CarHistoryDetailedViewDto carHistoryDetailedViewDto = new CarHistoryDetailedViewDto();
            List<CarHistory> carHistories = repository.findByDateBetweenAndPlateNumber(carHistoryDayStatsDto.getFirstDate(), carHistoryDayStatsDto.getLastDate(),plateNumber);
            carHistoryDetailedViewDto.setCarHistoryDayStatsDtos(carHistoryDayStatsDto);
            carHistoryDetailedViewDto.setCarHistory(carHistories);
            carHistoryDetailedViewDtos.add(carHistoryDetailedViewDto);
        }
        return carHistoryDetailedViewDtos;
    }

    public Optional<CarHistory> getCarHistoryById(Long id) {
        return repository.findById(id);
    }

    public CarHistory createCarHistory(CarHistory carHistory) {
        return repository.save(carHistory);
    }

    public CarHistory updateCarHistory(Long id, CarHistory carHistoryDetails) {
        CarHistory carHistory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CarHistory not found with id: " + id));


        return repository.save(carHistory);
    }

    public void deleteCarHistory(Long id) {
        repository.deleteById(id);
    }
}
