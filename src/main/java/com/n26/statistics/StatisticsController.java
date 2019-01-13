package com.n26.statistics;

import com.n26.transaction.ConcurrentTransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsController.class);

    private ConcurrentTransactionService transactionService;

    @Autowired
    StatisticsController(ConcurrentTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(produces = "application/json")
    public StatisticsDTO getStatistics() {
        final StatisticsDO statistics = transactionService.getStatistics();
        final StatisticsDTO statisticsDTO = StatisticsMapper.map(statistics);
        LOG.info("Statistics : {}", statisticsDTO.toString());
        return statisticsDTO;
    }
}
