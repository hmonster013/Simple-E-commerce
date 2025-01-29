package com.de013.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.de013.model.Report;
import com.de013.repository.ReportRepository;
import com.de013.utils.DateUtils;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public Report findOrCreate(String type, String date) {
        Report report = reportRepository.findByTypeAndDate(type, date);
        if (report == null) {
            report = new Report(type, DateUtils.toDateddMMyyyyHHmmss(date));
        }

        reportRepository.save(report);
        
        return report;
    }

    public List<Report> saveAll(List<Report> reports) {
        return reportRepository.saveAll(reports);
    }

    public List<Report> findByTypeAndTime(String type, String fromDate, String toDate) {
        return reportRepository.findByTypeAndTime(type, fromDate, toDate);
    }
}
