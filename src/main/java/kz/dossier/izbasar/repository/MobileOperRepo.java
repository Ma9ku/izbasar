package kz.dossier.izbasar.repository;

import kz.dossier.izbasar.model.MobileOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface MobileOperRepo extends JpaRepository<MobileOperator, Long> {
    @Query(value = "SELECT \n" +
            "    DATE(time_period) AS record_date,\n" +
            "    COUNT(*) AS record_count,\n" +
            "    MIN(time_period) AS start_time,\n" +
            "    MAX(time_period) AS end_time\n" +
            "FROM \n" +
            "    public.mobile_operators\n" +
            "WHERE\n" +
            "    DATE(time_period) >= ?1 AND DATE(time_period) <= ?2\n" +
            "    AND isdn_number = ?3 \n" +
            "GROUP BY \n" +
            "    DATE(time_period)\n" +
            "ORDER BY \n" +
            "    record_date;\n", nativeQuery = true)
    List<Object[]> getStatsByNumberAndDate(LocalDate dateFrom,
                                          LocalDate dateTo,
                                          String number);


    @Query(value = "SELECT \n" +
            "    EXTRACT(YEAR FROM time_period) AS year,\n" +
            "    EXTRACT(MONTH FROM time_period) AS month\n" +
            "FROM \n" +
            "    mobile_operators\n" +
            "WHERE \n" +
            "    isdn_number = ?1\n" +
            "    AND time_period >= ?2 \n" +
            "    AND time_period <= ?3\n" +
            "GROUP BY \n" +
            "    EXTRACT(YEAR FROM time_period), EXTRACT(MONTH FROM time_period)\n" +
            "ORDER BY \n" +
            "    year, month;\n", nativeQuery = true)
    List<Object[]> getAppearanceByMonths(String phoneNumber, LocalDate dateFrom,
                                         LocalDate dateTo);

    @Query(value = "SELECT * FROM mobile_operators WHERE isdn_number = ?1 AND DATE(time_period) = ?2", nativeQuery = true)
    List<MobileOperator> getAll(String number, LocalDate date);
}
