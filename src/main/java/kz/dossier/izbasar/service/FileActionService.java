package kz.dossier.izbasar.service;

import kz.dossier.izbasar.dto.FileSummaryDto;
import kz.dossier.izbasar.model.CarUploadSummary;
import kz.dossier.izbasar.repository.CarUploadSummaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileActionService {
    @Autowired
    CarUploadSummaryRepo carUploadSummaryRepo;

    public List<FileSummaryDto> getSummaries() {
        // Fetch all car upload summaries from the repository
        List<CarUploadSummary> carSummaries = carUploadSummaryRepo.findAll();

        // Define the DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Map each CarUploadSummary to a FileSummaryDto
        List<FileSummaryDto> fileSummaryDtos = carSummaries.stream()
                .map(carUploadSummary -> {
                    // Create and populate the DTO
                    FileSummaryDto dto = new FileSummaryDto();
                    dto.setId(carUploadSummary.getId());
                    dto.setStatus(true); // Assuming status is true for simplicity, adjust as needed
                    dto.setType(1); // Assuming type is 1 for simplicity, adjust as needed
                    dto.setIdentifier(carUploadSummary.getPlateNumber());
                    dto.setFixationNumber((long) carUploadSummary.getNumFixations()); // Convert numFixations to Long

                    // Format LocalDateTime to String
                    String formattedDateFrom = carUploadSummary.getDateFrom().format(formatter);
                    String formattedDateTo = carUploadSummary.getDateTo().format(formatter);

                    dto.setDateFrom(formattedDateFrom);
                    dto.setDateTo(formattedDateTo);

                    return dto;
                })
                .collect(Collectors.toList());

        // Return the list of DTOs
        return fileSummaryDtos;
    }
}
