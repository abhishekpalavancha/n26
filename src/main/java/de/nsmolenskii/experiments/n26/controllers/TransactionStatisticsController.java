package de.nsmolenskii.experiments.n26.controllers;

import de.nsmolenskii.experiments.n26.domains.TransactionStatistic;
import de.nsmolenskii.experiments.n26.services.MetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class TransactionStatisticsController {

    private final MetricsService metricsService;

    @Autowired
    public TransactionStatisticsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping(produces = "application/json")
    public TransactionStatistic getTransactionStatistics() {
        return metricsService.getTransactionStatistics();
    }
}
