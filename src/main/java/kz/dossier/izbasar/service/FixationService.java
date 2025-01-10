package kz.dossier.izbasar.service;

import kz.dossier.izbasar.dto.ExactFixationInfoDto;
import kz.dossier.izbasar.dto.FixationGroupDto;
import kz.dossier.izbasar.model.CarHistory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class FixationService {

    public List<FixationGroupDto> groupFixations(List<CarHistory> carHistoryList) {
        List<FixationGroupDto> groupedFixations = new ArrayList<>();
        FixationGroupDto currentGroup = null;

        for (int i = 0; i < carHistoryList.size(); i++) {
            CarHistory currentFixation = carHistoryList.get(i);
            LocalDateTime currentDate = currentFixation.getDate();

            if (currentGroup == null || (ChronoUnit.MINUTES.between(currentGroup.getEndTime(), currentDate) >= 20)) {
                // Start a new group if it's the first fixation or there's a 20-minute gap
                if (currentGroup != null) {
                    groupedFixations.add(currentGroup);
                }
                currentGroup = new FixationGroupDto();
                currentGroup.setStartTime(currentDate);
                currentGroup.setEndTime(currentDate);
                currentGroup.setFixations(new ArrayList<>());
                currentGroup.setFixNumber(1);
                currentGroup.setDuration("00:00");
            } else {
                // Extend the current group
                currentGroup.setEndTime(currentDate);
                currentGroup.setFixNumber(currentGroup.getFixNumber() + 1);
            }

            // Create ExactFixationInfoDto for the current fixation
            ExactFixationInfoDto fixationInfo = new ExactFixationInfoDto();
            fixationInfo.setCoordinates(currentFixation.getCoordinates());
            fixationInfo.setAddress(currentFixation.getLocation());
            fixationInfo.setDate(currentFixation.getDate());
            fixationInfo.setOrderId(currentGroup.getFixations().size() + 1);
            fixationInfo.setMinioImage(currentFixation.getMinioImage());

            long differenceInMinutes = ChronoUnit.MINUTES.between(currentGroup.getStartTime(), currentGroup.getEndTime());
            currentGroup.setDuration(differenceInMinutes + " мин.");
            // Add the fixation to the current group
            currentGroup.getFixations().add(fixationInfo);
        }

        if (currentGroup != null) {
            groupedFixations.add(currentGroup);
        }

        return groupedFixations;
    }
}
