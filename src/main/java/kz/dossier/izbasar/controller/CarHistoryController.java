package kz.dossier.izbasar.controller;
import kz.dossier.izbasar.dto.CarHistoryDayStatsDto;
import kz.dossier.izbasar.dto.CarHistoryDetailedViewDto;
import kz.dossier.izbasar.dto.CarHistoryStatsDTO;
import kz.dossier.izbasar.model.CarHistory;
import kz.dossier.izbasar.service.CarHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/car-history")
public class CarHistoryController {

    @Autowired
    private CarHistoryService service;

    @GetMapping
    public List<CarHistory> getAllCarHistories() {
        return service.getAllCarHistories();
    }

    @GetMapping("/{id}")
    public Optional<CarHistory> getCarHistoryById(@PathVariable Long id) {
        Optional<CarHistory> car = service.getCarHistoryById(id);
        System.out.println(car.get().getLocation());
        return car;
    }

    @GetMapping("/stats")
    public List<CarHistoryStatsDTO> getCarHistoryStats(@RequestParam String plate_number) {
        return service.getCarHistoryStatistics(plate_number);
    }
    @GetMapping("/day-stats")
    public List<CarHistoryDayStatsDto> getCarHistoryDayStats(@RequestParam String date) {
        return service.getCarHistoryDatStats(date);
    }

    @GetMapping("/day-stats-detailed")
    public List<CarHistoryDetailedViewDto> getCarHistoryDayStats(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String plateNumber, @RequestParam Integer interval) {
        return service.carHistoryDetailedViewDtos(startDate, endDate ,plateNumber, interval);
    }
    @GetMapping("/day-stats-collapse")
    public List<CarHistory> getCarHistoryBetweenDates(@RequestParam String startDate, @RequestParam String endDate,@RequestParam String plateNumber){
        return service.getCarHistoryBetweenDate(startDate,endDate,plateNumber);
    }
    @GetMapping("/car-history-pageable")
    public Page<CarHistory> getCarHistoryPage(Pageable pageable){
        return service.getCarsPage(pageable);
    }
    @PostMapping
    public CarHistory createCarHistory(@RequestBody CarHistory carHistory) {
        return service.createCarHistory(carHistory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarHistory> updateCarHistory(@PathVariable Long id, @RequestBody CarHistory carHistoryDetails) {
        return ResponseEntity.ok(service.updateCarHistory(id, carHistoryDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarHistory(@PathVariable Long id) {
        service.deleteCarHistory(id);
        return ResponseEntity.noContent().build();
    }
}
