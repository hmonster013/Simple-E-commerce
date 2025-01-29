package com.de013.task;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.de013.dto.FilterVO;
import com.de013.model.Order;
import com.de013.model.Paging;
import com.de013.model.Report;
import com.de013.service.OrderService;
import com.de013.service.ReportService;
import com.de013.utils.DateUtils;
import com.de013.utils.JConstants;
import com.de013.utils.JConstants.ReportType;

@Configuration
@EnableScheduling
public class DailyReportTask {
    
    private final static Logger log = LoggerFactory.getLogger(DailyReportTask.class);

    @Autowired
    private ReportService reportService;

    @Autowired
    private OrderService orderService;

    // @Scheduled(initialDelay = 1000, fixedRate = 99999999)
    public void dailyReportDate() {
        for (int i = 0; i <= 8 ; i++) {
            // dailyReport(DateUtils.addDate(i));  
        }
    }

    @Scheduled(initialDelay = 1000, fixedRate = 99999999)
    public void dailyReport() {
        try {
            Date date = new Date();
            String today = DateUtils.toddMMyyyyHHmmss(date);
            String ddMMyyyy = DateUtils.toString(date);

            log.info("Start dailyReport date [{}]", today);

            List<Report> reports = new ArrayList<Report>();
            Report report = null;

            // Total sale amount (Status != PENDING)
            report = reportService.findOrCreate(ReportType.SALES_AMOUNT.name(), today);

            FilterVO request = new FilterVO();
            request.setSize(Integer.valueOf(JConstants.SIZE_MAX));
            request.setOrderHistory(true);

            Pageable paging = new Paging().getPageRequest(request);
            Page<Order> orders = orderService.search(request, paging);

            BigDecimal salesAmount = new BigDecimal(0);

            for (Order order : orders) {
                salesAmount = salesAmount.add(order.getTotalAmount());
            }
            report.setAmount(salesAmount);
            reports.add(report);

            // Total sale count (Status != PENDING)
            report = reportService.findOrCreate(ReportType.SALES_COUNT.name(), today);
            report.setAmount(new BigDecimal(orders.getTotalElements()));
            reports.add(report);

            reportService.saveAll(reports);
            log.info("DONE dailyReport date [{}]", today);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
