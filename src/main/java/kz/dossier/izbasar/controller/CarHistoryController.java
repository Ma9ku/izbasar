package kz.dossier.izbasar.controller;
import kz.dossier.izbasar.dto.*;
import kz.dossier.izbasar.model.CarHistory;
import kz.dossier.izbasar.service.CarHistoryService;
import kz.dossier.izbasar.service.FileActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/car-history")
public class CarHistoryController {

    @Autowired
    CarHistoryService service;
    @Autowired
    FileActionService fileActionService;

    @GetMapping("/get-file-summaries")
    public ResponseEntity<List<FileSummaryDto>> getFileSummaries() {
        return ResponseEntity.ok(fileActionService.getSummaries());
    }

    @PostMapping("/get-stats-by-groups")
    public ResponseEntity<Map<String, List<CarHistoryStatsDTO>>> getCarHistoryStatsByGroups(
            @RequestBody List<Group> groups) {

        Map<String, List<CarHistoryStatsDTO>> result = service.getGroups(groups);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-stats-by-plate-date")
    public ResponseEntity<List<CarHistoryStatsDTO>> getCarHistoryStats(
            @RequestParam String plateNumber,
            @RequestParam String dateFrom,
            @RequestParam String dateTo) {
        List<CarHistoryStatsDTO> stats = service.getCarHistoryStats(dateFrom, dateTo, plateNumber);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/get-appearance")
    public ResponseEntity<List<YearAndMonthsAppearanceDto>> getAppearance(@RequestParam String plateNumber,
                                                                          @RequestParam String dateFrom,
                                                                          @RequestParam String dateTo) {
        List<YearAndMonthsAppearanceDto> results = service.getMonthsAppearance(dateFrom, dateTo, plateNumber);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/get-days")
    public ResponseEntity<List<Date>> getDays(@RequestParam String plateNumber) {
        List<Date> results = service.getFixationsDays(plateNumber);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/get-groups")
    public ResponseEntity<List<FixationGroupDto>> getDays(@RequestParam String plateNumber,  @RequestParam String date) {
        List<FixationGroupDto> results = service.getGroups(plateNumber, date);
        return ResponseEntity.ok(results);
    }


//    @GetMapping
//    public List<CarHistory> getAllCarHistories() {
//        return service.getAllCarHistories();
//    }
//
//    @GetMapping("/{id}")
//    public Optional<CarHistory> getCarH istoryById(@PathVariable Long id) {
//        Optional<CarHistory> car = service.getCarHistoryById(id);
//        System.out.println(car.get().getLocation());
//        return car;
//    }
//
//    @GetMapping("/stats")
//    public List<CarHistoryStatsDTO> getCarHistoryStats(@RequestParam String plate_number) {
//        return service.getCarHistoryStatistics(plate_number);
//    }
//    @GetMapping("/day-stats")
//    public List<CarHistoryDayStatsDto> getCarHistoryDayStats(@RequestParam String date) {
//        return service.getCarHistoryDatStats(date);
//    }
//
//    @GetMapping("/day-stats-detailed")
//    public List<CarHistoryDetailedViewDto> getCarHistoryDayStats(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String plateNumber, @RequestParam Integer interval) {
//        return service.carHistoryDetailedViewDtos(startDate, endDate ,plateNumber, interval);
//    }
//    @GetMapping("/day-stats-collapse")
//    public List<CarHistory> getCarHistoryBetweenDates(@RequestParam String startDate, @RequestParam String endDate,@RequestParam String plateNumber){
//        return service.getCarHistoryBetweenDate(startDate,endDate,plateNumber);
//    }
//    @GetMapping("/car-history-pageable")
//    public Page<CarHistory> getCarHistoryPage(Pageable pageable){
//        return service.getCarsPage(pageable);
//    }
//    @PostMapping
//    public CarHistory createCarHistory(@RequestBody CarHistory carHistory) {
//        return service.createCarHistory(carHistory);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CarHistory> updateCarHistory(@PathVariable Long id, @RequestBody CarHistory carHistoryDetails) {
//        return ResponseEntity.ok(service.updateCarHistory(id, carHistoryDetails));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCarHistory(@PathVariable Long id) {
//        service.deleteCarHistory(id);
//        return ResponseEntity.noContent().build();
//    }
}
