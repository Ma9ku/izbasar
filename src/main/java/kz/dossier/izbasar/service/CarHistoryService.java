package kz.dossier.izbasar.service;

import kz.dossier.izbasar.dto.*;
import kz.dossier.izbasar.model.CarHistory;
import kz.dossier.izbasar.repository.CarHistoryRepository;
import kz.dossier.izbasar.repository.MobileOperRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarHistoryService {

    @Autowired
    private CarHistoryRepository repository;
    @Autowired
    private FixationService fixationService;
    @Autowired
    private MobileOperRepo mobileOperRepo;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<FixationGroupDto> getGroups(String plateNumber, String date) {
        LocalDate dt = LocalDate.parse(date);
        List<CarHistory> carHistories = repository.getFixationsForDate(plateNumber, dt);

        List<FixationGroupDto> groups = fixationService.groupFixations(carHistories);

        return groups;
    }
    public List<Date> getFixationsDays(String plateNumber) {

        List<Date> fixationDates = repository.getDays(plateNumber);

        return fixationDates;
    }
    public List<YearAndMonthsAppearanceDto> getMonthsAppearance(String dateFrom, String dateTo, String plateNumber) {
        // Convert string dates to LocalDate
        LocalDate from = LocalDate.parse(dateFrom);
        LocalDate to = LocalDate.parse(dateTo);

        // Execute the query
        List<Object[]> queryResults = repository.getAppearanceByMonths(plateNumber, from, to);

        // Process the results
        Map<Integer, List<Integer>> yearToMonthsMap = new HashMap<>();

        for (Object[] result : queryResults) {
            Integer year = ((Number) result[0]).intValue();
            Integer month = ((Number) result[1]).intValue();

            yearToMonthsMap
                    .computeIfAbsent(year, k -> new ArrayList<>())
                    .add(month);
        }

        // Convert the map into a list of YearAndMonthsAppearanceDto
        return yearToMonthsMap.entrySet().stream()
                .map(entry -> new YearAndMonthsAppearanceDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Map<String, List<CarHistoryStatsDTO>> getGroups(List<Group> groups) {
        DateTimeFormatter russianDateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", Locale.forLanguageTag("ru"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Map to store the final result with group numbers as keys
        Map<String, List<CarHistoryStatsDTO>> resultMap = new HashMap<>();

        // List to store all stats for intersection processing
        List<CarHistoryStatsDTO> allStats = new ArrayList<>();

        // Fetch the stats based on group status and add them to allStats
        for (Group a : groups) {
            List<CarHistoryStatsDTO> stats;
            if (a.getStatus()) {
                // Get car history stats if status is true
                stats = getCarHistoryStats(a.getDateFrom(), a.getDateTo(), a.getNumber());
            } else {
                // Get mobile history stats if status is false
                stats = getMobHistoryStats(a.getDateFrom(), a.getDateTo(), a.getNumber());
            }

            // Add group number to each DTO for easy access later
            for (CarHistoryStatsDTO dto : stats) {
                dto.setNumber(a.getNumber()); // Assuming CarHistoryStatsDTO has setGroupNumber method
            }

            allStats.addAll(stats); // Collect all stats for intersection processing

            // Add stats to result map using the group's number as the key
            resultMap.put(a.getNumber(), stats);
        }

        // Group the stats by dateLocal for intersection processing
        Map<LocalDate, List<CarHistoryStatsDTO>> groupedByDate = allStats.stream()
                .collect(Collectors.groupingBy(CarHistoryStatsDTO::getDateLocal));

        // For each date, check for intersections and handle combined stats
        for (Map.Entry<LocalDate, List<CarHistoryStatsDTO>> entry : groupedByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<CarHistoryStatsDTO> intersectingStats = entry.getValue();

            // Combine fixCount and find min/max times for the intersections
            long combinedFixCount = intersectingStats.stream()
                    .mapToLong(CarHistoryStatsDTO::getFixCount)
                    .sum();

            LocalDateTime minStartDate = intersectingStats.stream()
                    .map(CarHistoryStatsDTO::getStartDate)
                    .min(LocalDateTime::compareTo)
                    .orElse(null);

            LocalDateTime maxEndDate = intersectingStats.stream()
                    .map(CarHistoryStatsDTO::getEndDate)
                    .max(LocalDateTime::compareTo)
                    .orElse(null);

            // Create the combined intersection DTO
            CarHistoryStatsDTO intersectionDTO = new CarHistoryStatsDTO();
            intersectionDTO.setDateLocal(date);
            intersectionDTO.setFixCount(combinedFixCount);
            intersectionDTO.setStartDate(minStartDate);
            intersectionDTO.setEndDate(maxEndDate);
            intersectionDTO.setDate(date != null ? date.format(russianDateFormatter) : null);
            intersectionDTO.setTimeStart((minStartDate != null) ? minStartDate.toLocalTime().format(timeFormatter) : null);
            intersectionDTO.setTimeEnd((maxEndDate != null) ? maxEndDate.toLocalTime().format(timeFormatter) : null);
            intersectionDTO.setType("intersections");

            // Generate the intersection key based on the group numbers, ensuring no duplicates
            Set<String> groupNumbers = intersectingStats.stream()
                    .map(dto -> dto.getNumber()) // Use groupNumber
                    .distinct() // Remove duplicates
                    .collect(Collectors.toSet());

            // If there's an intersection, we create a new key with a combination of group numbers
            if (groupNumbers.size() > 1) {
                String intersectionKey = String.join("+", groupNumbers);
                resultMap.put(intersectionKey, Collections.singletonList(intersectionDTO));
            }

            // Add individual group stats to the result map if not already added
            for (String groupNumber : groupNumbers) {
                if (!resultMap.containsKey(groupNumber)) {
                    List<CarHistoryStatsDTO> groupStats = intersectingStats.stream()
                            .filter(dto -> dto.getNumber().equals(groupNumber))
                            .collect(Collectors.toList());
                    resultMap.put(groupNumber, groupStats);
                }
            }
        }

        // Return the map with group numbers as keys and lists of CarHistoryStatsDTO as values
        return resultMap;
    }


    public List<CarHistoryStatsDTO> getMobHistoryStats(String dateFrom, String dateTo, String number) {
        LocalDate parsedDateFrom = LocalDate.parse(dateFrom, DATE_FORMATTER);
        LocalDate parsedDateTo = LocalDate.parse(dateTo, DATE_FORMATTER);
        List<Object[]> results = mobileOperRepo.getStatsByNumberAndDate(parsedDateFrom, parsedDateTo, number);
        List<CarHistoryStatsDTO> stats = new ArrayList<>();

        for (Object[] row : results) {
            // Convert java.sql.Date to LocalDate properly
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
            CarHistoryStatsDTO dto = new CarHistoryStatsDTO(date,
                    (Long) row[1],
                    ((Timestamp) row[2]).toLocalDateTime(),
                    ((Timestamp) row[3]).toLocalDateTime(), "phone");
            stats.add(dto);
        }

        return stats;
    }
    public List<CarHistoryStatsDTO> getCarHistoryStats(String dateFrom, String dateTo, String plateNumber) {
        LocalDate parsedDateFrom = LocalDate.parse(dateFrom, DATE_FORMATTER);
        LocalDate parsedDateTo = LocalDate.parse(dateTo, DATE_FORMATTER);
        List<Object[]> results = repository.getStatsByPlateAndDate(parsedDateFrom, parsedDateTo, plateNumber);
        List<CarHistoryStatsDTO> stats = new ArrayList<>();

        for (Object[] row : results) {
            // Convert java.sql.Date to LocalDate properly
            LocalDate date = ((java.sql.Date) row[0]).toLocalDate();
            CarHistoryStatsDTO dto = new CarHistoryStatsDTO(date,
                    (Long) row[1],
                    ((Timestamp) row[2]).toLocalDateTime(),
                    ((Timestamp) row[3]).toLocalDateTime());
            stats.add(dto);
        }

        return stats;
    }


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
