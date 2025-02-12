package com.de013.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.de013.service.ProductService;
import com.de013.service.ReportService;

@Configuration
@EnableScheduling
public class WarehouseTask {
    private final static Logger log = LoggerFactory.getLogger(WarehouseTask.class);
    
    private ReportService reportService;

    private ProductService productService;

    public void stockQuantityNotification() {
        
    }
}
