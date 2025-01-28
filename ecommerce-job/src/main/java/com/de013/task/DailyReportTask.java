package com.de013.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class DailyReportTask {
    
    private final static Logger log = LoggerFactory.getLogger(DailyReportTask.class);

    @Scheduled(initialDelay = 1000, fixedRate = 99999999)
    public void dailyReport() {
        try {
            log.info("New Year");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
