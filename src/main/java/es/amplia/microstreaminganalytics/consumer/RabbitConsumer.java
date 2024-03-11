package es.amplia.microstreaminganalytics.consumer;

import es.amplia.microstreaminganalytics.config.PublisherConfig;
import es.amplia.microstreaminganalytics.dto.MessageDTO;
import es.amplia.microstreaminganalytics.interfaces.IMessageDTOService;
import es.amplia.microstreaminganalytics.interfaces.IStatisticsService;
import es.amplia.microstreaminganalytics.model.Statistics;
import es.amplia.microstreaminganalytics.util.EntityName;
import es.amplia.microstreaminganalytics.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RabbitConsumer {
    @Autowired
    PublisherConfig publisherConfig;

    @Autowired
    IMessageDTOService iMessageDTOService;

    @Autowired
    IStatisticsService iStatisticsService;
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
        this.getStatisticsUntilNow();
        makeSlow();
    }

    // This function is going to populate the Statistics class with the parameter it gets.
    public void getStatisticsUntilNow () {
        try {
        iStatisticsService.saveStatistics(Utilities.getStatisticsObject(EntityName.TEMPERATURE, weatherDataMap.get(EntityName.TEMPERATURE)));
        iStatisticsService.saveStatistics(Utilities.getStatisticsObject(EntityName.HUMIDITY, weatherDataMap.get(EntityName.HUMIDITY)));
        iStatisticsService.saveStatistics(Utilities.getStatisticsObject(EntityName.PRESSURE, weatherDataMap.get(EntityName.PRESSURE)));
        iStatisticsService.saveStatistics(Utilities.getStatisticsObject(EntityName.WINDSPEED, weatherDataMap.get(EntityName.WINDSPEED)));

        }catch (Exception e){
            e.printStackTrace();
            log.error("getStatisticsUntilNow, found an error to get Statistics until now. {}", e.getMessage());
        }
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
