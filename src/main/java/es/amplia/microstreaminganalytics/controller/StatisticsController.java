package es.amplia.microstreaminganalytics.controller;

import es.amplia.microstreaminganalytics.interfaces.IStatisticsService;
import es.amplia.microstreaminganalytics.model.Statistics;
import es.amplia.microstreaminganalytics.util.EntityName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/getStatisticsByID")
    public ResponseEntity<Statistics> getStatisticsByID (@RequestBody Long id) {
        Statistics statisticsOutput;
        log.info("getStatisticsByID,getting Statistics With ID: {}", id);

        try {
            statisticsOutput = iStatisticsService.getStatisticsByID(id);

        } catch(Exception e) {
            e.printStackTrace();
            log.error("getStatisticsByID, error getting Statistic with ID: {}. {}", id, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }


        return ResponseEntity.ok(statisticsOutput);
    }

    @PostMapping("/getStatisticsByDate")
    public ResponseEntity<List<Statistics> > getStatisticsByID (@RequestBody Date date) {
       List<Statistics> statisticsOutput;
        log.info("getStatisticsByID,getting Statistics With Date: {}", date);

        try {
            statisticsOutput = iStatisticsService.getStatisticsByDate(date);

        } catch(Exception e) {
            e.printStackTrace();
            log.error("getStatisticsByID, error getting Statistic with Date: {}. {}", date, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }


        return ResponseEntity.ok(statisticsOutput);
    }
    @PostMapping("/getStatisticsByEntityName")
    public ResponseEntity<List<Statistics>> getStatisticsByEntity (@RequestBody String en) {
        List<Statistics> statisticsOutput = new ArrayList<>();
        log.info("getStatisticsByEntity,getting Statistics With EntityName: {}", en);

        try {
            switch (en.toUpperCase()){
                case "TEMPERATURE":
                    statisticsOutput = iStatisticsService.getStatisticsByEntity(EntityName.TEMPERATURE);
                    break;
                case "HUMIDITY":
                    statisticsOutput = iStatisticsService.getStatisticsByEntity(EntityName.HUMIDITY);
                    break;
                case "PRESSURE":
                    statisticsOutput = iStatisticsService.getStatisticsByEntity(EntityName.PRESSURE);
                    break;
                case "WINDSPEED":
                    statisticsOutput = iStatisticsService.getStatisticsByEntity(EntityName.WINDSPEED);
                    break;
                default:
                    ResponseEntity.noContent().build();
            }


            if(statisticsOutput.isEmpty()) {
                log.info("getStatisticsByEntity, EntityName with name {}, has not results", en);
                return ResponseEntity.noContent().build();
            }
        } catch(Exception e) {
            e.printStackTrace();
            log.error("getStatisticsByEntity, error getting Statistic with EntityName: {}. {}", en, e.getMessage());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(statisticsOutput);
    }

}
