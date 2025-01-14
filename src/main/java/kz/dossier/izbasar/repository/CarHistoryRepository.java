package kz.dossier.izbasar.repository;

import kz.dossier.izbasar.model.CarHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CarHistoryRepository extends JpaRepository<CarHistory, Long> {

    @Query(value = "SELECT \n" +
            "    EXTRACT(YEAR FROM date) AS year,\n" +
            "    EXTRACT(MONTH FROM date) AS month\n" +
            "FROM \n" +
            "    car_history\n" +
            "WHERE \n" +
            "    plate_number = ?1 \n" +
            "    AND date >= ?2 AND date <= ?3" +
            "GROUP BY \n" +
            "    EXTRACT(YEAR FROM date), EXTRACT(MONTH FROM date)\n" +
            "ORDER BY \n" +
            "    year, month;\n", nativeQuery = true)
    List<Object[]> getAppearanceByMonths(String plateNumber, LocalDate dateFrom,
                                         LocalDate dateTo);

    @Query(value = "SELECT *\n" +
            "FROM \n" +
            "    car_history\n" +
            "WHERE \n" +
            "    plate_number  = ?1 \n" +
            "    AND DATE(date) = ?2;", nativeQuery = true)
    List<CarHistory> getAll(String number, LocalDate date);
    @Query(value = "SELECT DISTINCT CAST(\"date\" AS DATE) AS fixation_date\n" +
            "FROM car_history\n" +
            "WHERE plate_number = ?1\n" + // Ensure the end date is included
            "ORDER BY fixation_date;",
            nativeQuery = true)
    List<Date> getDays(String plateNumber);

    @Query(value = "SELECT *\n" +
            "FROM car_history\n" +
            "WHERE plate_number = ?1\n" +
            "  AND DATE(\"date\") = ?2\n" +
            "ORDER BY \"date\";",
            nativeQuery = true)
    List<CarHistory> getFixationsForDate(String plateNumber, LocalDate dateLocal);


    @Query(value = "SELECT \n" +
            "    DATE(date) AS record_date,\n" +
            "    COUNT(*) AS record_count,\n" +
            "    MIN(date) AS start_time,\n" +
            "    MAX(date) AS end_time\n" +
            "FROM \n" +
            "    car_history\n" +
            "WHERE\n" +
            "    DATE(date) >= ?1 AND DATE(date) <= ?2\n" +
            "    AND plate_number = ?3\n" +
            "GROUP BY \n" +
            "    DATE(date)\n" +
            "ORDER BY \n" +
            "    record_date;\n", nativeQuery = true)
    List<Object[]> getStatsByPlateAndDate(LocalDate dateFrom,
                                          LocalDate dateTo,
                                          String plateNumber);

    @Query(value = """
            SELECT 
                DATE(date) as record_date, 
                COUNT(*) as record_count, 
                MIN(date) as start_time, 
                MAX(date) as end_date
            FROM car_history where plate_number = ?1
            GROUP BY DATE(date)
            ORDER BY record_date
            """, nativeQuery = true)
    List<Object[]> getCarHistoryStats(String plate_number);
    @Query(value = "select * from car_history order by date asc", nativeQuery = true)
    Page<CarHistory> getAllPage(Pageable pageable);

    @Query(value = """
        WITH DataWithLag AS (
            SELECT *, LAG(date) OVER (ORDER BY date) AS prev_date
            FROM car_history ch
            WHERE date >= ?1 AND date <= ?2 AND plate_number = ?3
        ),
        FlaggedData AS (
            SELECT *, 
            CASE 
                WHEN prev_date IS NULL OR date - prev_date > MAKE_INTERVAL(mins => ?4) THEN 1 
                ELSE 0 
            END AS is_new_group
            FROM DataWithLag
        ),
        GroupedData AS (
            SELECT *, SUM(is_new_group) OVER (ORDER BY date) AS group_id
            FROM FlaggedData
        )
        SELECT 
            MIN(date) AS first_date,
            MAX(date) AS last_date,
            COUNT(*) AS count_between,
            EXTRACT(EPOCH FROM MAX(date) - MIN(date)) / 60 AS minutes
        FROM GroupedData
        GROUP BY group_id
        ORDER BY first_date
    """, nativeQuery = true)
    List<Object[]> getCarHistoryDay(LocalDateTime startDate, LocalDateTime endDate, String plateNumber, int interval);
    @Query(value = """
            with DataWithLag as(
            	select *, LAG(date) over (order by date) as prev_date
            	from car_history ch\s
            	where date(date) = ?1
            ),
            FlaggedData as(
            	select *, case\s
            		when prev_date is null or date - prev_date > interval '20 minutes' then 1 else 0
            	end as is_new_group
            	from DataWithLag
            ),
            GroupedData as(
            	select *, sum(is_new_group) over (order by date) as group_id
            	from FlaggedData
            )
            select\s
            	MIN(date) as first_date,
            	MAX(date) as last_date,
            	COUNT(*) as count_between,
            	EXTRACT(EPOCH FROM MAX(date) - MIN(date)) / 60 as minutes
            	from GroupedData
            	group by group_id
            	order by first_date
    """, nativeQuery = true)
    List<Object[]> getCarHistoryDay(LocalDate date);

    List<CarHistory> findByDateBetweenAndPlateNumber(LocalDateTime startDate, LocalDateTime endDate, String plateNumber);
}
