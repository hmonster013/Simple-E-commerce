package com.de013.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.de013.dto.FilterVO;
import com.de013.dto.ReportVO;
import com.de013.model.Report;
import com.de013.service.ReportService;
import com.de013.utils.URI;

@RestController
@RequestMapping(URI.V1 + URI.REPORT)
public class ReportController extends BaseController {
    private final static Logger log = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = URI.LIST + URI.TYPE + URI.FROM_DATE + URI.TO_DATE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity viewReportByType(
        @PathVariable("type") String type,
        @PathVariable("fromDate") String fromDate,
        @PathVariable("toDate") String toDate
    ) {
        log.info("Report type [{}] [{} -> {}]", type, fromDate, toDate);

        List<Report> reports = reportService.findByTypeAndTime(type, fromDate, toDate);
        List<ReportVO> result = reports.stream().map(Report::getVO).toList();

        return responseList(result);
    }
}
