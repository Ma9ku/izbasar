package kz.dossier.izbasar.repository;


import kz.dossier.izbasar.model.CarUploadSummary;
import kz.dossier.izbasar.model.MobileFixSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobileFixOperatorRepo extends JpaRepository<MobileFixSummary, Long> {
    @Query(value = "select * from mobile_fix_summary", nativeQuery = true)
    List<MobileFixSummary> findAll();
}
