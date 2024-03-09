package es.amplia.microstreaminganalytics.controller;

import es.amplia.microstreaminganalytics.interfaces.IStatisticsService;
import es.amplia.microstreaminganalytics.model.Statistics;
import es.amplia.microstreaminganalytics.util.EntityName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class StatisticsController {

    @Autowired
    private IStatisticsService iStatisticsService;

    @PostMapping("/getAllStatistics")
    public ResponseEntity<List<Statistics>> getAllStatistics () {
        List<Statistics> statistics;

        statistics = iStatisticsService.getAllStatistics();
        if(statistics.isEmpty()){
            log.error("An error occurred getting all Statistics, List size: {}", statistics.size());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(statistics);
    }

    @PostMapping("/getStatisticsUntilNow")
    public ResponseEntity<Map<EntityName, Statistics>> getStatisticsUntilNow () {
        Map<EntityName, Statistics> statisticsOutput;

        statisticsOutput = iStatisticsService.getStatisticsUntilNow();

        if(statisticsOutput.isEmpty()){
            log.info("getStatisticsUntilNow, It didn't find any Statistics");
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(statisticsOutput);
    }


}
