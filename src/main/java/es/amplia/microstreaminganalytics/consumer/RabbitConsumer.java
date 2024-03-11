package es.amplia.microstreaminganalytics.consumer;

import es.amplia.microstreaminganalytics.config.PublisherConfig;
import es.amplia.microstreaminganalytics.dto.MessageDTO;
import es.amplia.microstreaminganalytics.interfaces.IMessageDTOService;
import es.amplia.microstreaminganalytics.model.Statistics;
import es.amplia.microstreaminganalytics.util.EntityName;
import es.amplia.microstreaminganalytics.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class RabbitConsumer {
    @Autowired
    PublisherConfig publisherConfig;

    @Autowired
    IMessageDTOService iMessageDTOService;
    private Map<EntityName, List<Double>> weatherDataMap;

    public RabbitConsumer() {
        weatherDataMap = new HashMap<>();
        weatherDataMap.put(EntityName.TEMPERATURE, new ArrayList<>());
        weatherDataMap.put(EntityName.HUMIDITY, new ArrayList<>());
        weatherDataMap.put(EntityName.PRESSURE, new ArrayList<>());
        weatherDataMap.put(EntityName.WINDSPEED, new ArrayList<>());
    }

    @RabbitListener(queues = {"#{publisherConfig.queue}"})
    public void receiver(@Payload MessageDTO message) {
        log.info("Received message with ID: {}", message.getId());

        weatherDataMap.get(EntityName.TEMPERATURE).add(message.getData().getTemperature());
        weatherDataMap.get(EntityName.HUMIDITY).add(message.getData().getHumidity());
        weatherDataMap.get(EntityName.PRESSURE).add(message.getData().getPressure());
        weatherDataMap.get(EntityName.WINDSPEED).add(message.getData().getWindSpeed());

        iMessageDTOService.saveProduct(message);

        makeSlow();
    }

    // This function is going to populate the Statistics class with the parameter it gets.
    public Statistics calculateStatistics(EntityName entityName) {
        Statistics statistics = new Statistics();

        statistics.setDate(new Date());

        try {
            switch (entityName) {
                case EntityName.TEMPERATURE:
                    statistics.setEntityName(EntityName.TEMPERATURE);
                    statistics.calculateStatistics(Utilities.convertToArray(weatherDataMap.get(EntityName.TEMPERATURE)));
                    break;
                case EntityName.HUMIDITY:
                    statistics.setEntityName(EntityName.HUMIDITY);
                    statistics.calculateStatistics(Utilities.convertToArray(weatherDataMap.get(EntityName.HUMIDITY)));
                    break;
                case EntityName.PRESSURE:
                    statistics.setEntityName(EntityName.PRESSURE);
                    statistics.calculateStatistics(Utilities.convertToArray(weatherDataMap.get(EntityName.PRESSURE)));
                    break;
                case EntityName.WINDSPEED:
                    statistics.setEntityName(EntityName.WINDSPEED);
                    statistics.calculateStatistics(Utilities.convertToArray(weatherDataMap.get(EntityName.WINDSPEED)));
                    break;
                default:
                    throw new IllegalArgumentException("Wrong EntityName");
            }

        } catch (IllegalArgumentException e) {
            log.error("Error: {}", e.getMessage());
        }

        return statistics;
    }

    // This function just make the Thread await for ? seconds
    private void makeSlow(){
        try {
            Thread.sleep(publisherConfig.getSleepingTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
