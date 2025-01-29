package com.de013.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.de013.model.Report;
import java.util.List;
import java.util.Date;


@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT u FROM Report u WHERE 1 = 1 "
        + " AND u.date = to_date(:date,'DD-MM-YYYY HH24:MI:SS') "
        + " AND u.type = :type"
    )
	public Report findByTypeAndDate(String type, String date);

    @Query("SELECT u FROM Report u WHERE 1 = 1 "
        + " AND u.date >= to_date(:fromDate,'DD-MM-YYYY HH24:MI:SS') "
        + " AND u.date <= to_date(:toDate,'DD-MM-YYYY HH24:MI:SS') "
        + " AND u.type = :type"
    )
    public List<Report> findByTypeAndTime(String type, String fromDate, String toDate);
}
