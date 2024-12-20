package kz.dossier.izbasar.repository;

import kz.dossier.izbasar.model.CarHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CarHistoryRepository extends JpaRepository<CarHistory, Long> {
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
