package kz.dossier.izbasar.repository;

import kz.dossier.izbasar.model.CarHistory;
import kz.dossier.izbasar.model.CarUploadSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarUploadSummaryRepo extends JpaRepository<CarUploadSummary, Long> {
    @Query(value = "select * from car_upload_summary", nativeQuery = true)
    List<CarUploadSummary> findAll();
}
