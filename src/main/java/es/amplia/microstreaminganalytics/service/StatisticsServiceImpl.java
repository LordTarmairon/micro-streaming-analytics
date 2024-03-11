package es.amplia.microstreaminganalytics.service;

import es.amplia.microstreaminganalytics.dto.MessageDTO;
import es.amplia.microstreaminganalytics.interfaces.IMessageDTOService;
import es.amplia.microstreaminganalytics.interfaces.IStatisticsService;
import es.amplia.microstreaminganalytics.model.Statistics;
import es.amplia.microstreaminganalytics.util.EntityName;
import es.amplia.microstreaminganalytics.repository.StatisticsRepository;
import es.amplia.microstreaminganalytics.util.Utilities;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatisticsServiceImpl implements IStatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private IMessageDTOService iMessageDTOService;

    @Override
    public Statistics getStatisticsByID(Long id) {
        log.info("Finding Statistics by ID: {}",id);
        return statisticsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("getStatisticsByID, it gets an error finding a Statistics by ID: "+ id));
    }

    @Override
    public List<Statistics> getStatisticsByEntity(EntityName entity) {
        List<Statistics> output;

        log.info("Finding Statistics by EntityName: {}",entity);

        output = statisticsRepository.findByEntityName(entity);

        if(output.isEmpty() || output == null) {
            throw new EntityNotFoundException("getStatisticsByEntity, it gets an error finding a Statistics by ID: "+ entity);
        }

        return output;
    }
    @Override
    public List<Statistics> getAllStatistics() {
        List<Statistics> output;

        log.info("Getting all Statistics");
        output = statisticsRepository.findAll();

        if(output.isEmpty() || output == null) {
            throw new EntityNotFoundException("getAllStatistics, it gets an error getting all Statistics");
        }

        return output;
    }

    @Override
    @Transactional
    public Statistics saveStatistics(Statistics statistics) {
        try {
            log.info("Saving Statistics with ID: {}", statistics.getId());
            return statisticsRepository.save(statistics);
        } catch (DataAccessException e) {
            log.error("Error saving Statistics with ID: {}. {}", statistics.getId(), e.getMessage());
            throw new RuntimeException("Error saving Statistics: " + e.getMessage(), e);
        }
    }

    @Override
    public Map<EntityName, Statistics> getStatisticsUntilNow () {
        List<MessageDTO> messageDTOS;
        Map<EntityName, Statistics> weatherDataMap = new HashMap<>();

        messageDTOS = iMessageDTOService.getAllMessageDTO();

        // SET WEATHERDATA
        try{
            weatherDataMap.put(EntityName.TEMPERATURE, Utilities.getStatisticsObject(EntityName.TEMPERATURE, messageDTOS.stream()
                    .map(messageDTO -> messageDTO.getData().getTemperature())
                    .collect(Collectors.toList())));
            weatherDataMap.put(EntityName.HUMIDITY, Utilities.getStatisticsObject(EntityName.HUMIDITY, messageDTOS.stream()
                    .map(messageDTO -> messageDTO.getData().getHumidity())
                    .collect(Collectors.toList())));
            weatherDataMap.put(EntityName.PRESSURE, Utilities.getStatisticsObject(EntityName.PRESSURE, messageDTOS.stream()
                    .map(messageDTO -> messageDTO.getData().getPressure())
                    .collect(Collectors.toList())));
            weatherDataMap.put(EntityName.WINDSPEED, Utilities.getStatisticsObject(EntityName.WINDSPEED, messageDTOS.stream()
                    .map(messageDTO -> messageDTO.getData().getWindSpeed())
                    .collect(Collectors.toList())));

            // PERSISTING DATES ON DB
            this.saveStatistics(weatherDataMap.get(EntityName.TEMPERATURE));
            this.saveStatistics(weatherDataMap.get(EntityName.HUMIDITY));
            this.saveStatistics(weatherDataMap.get(EntityName.PRESSURE));
            this.saveStatistics(weatherDataMap.get(EntityName.WINDSPEED));
        }catch (Exception e){
            e.printStackTrace();
            log.error("getStatisticsUntilNow, found an error to get Statistics until now. {}", e.getMessage());
        }

        return weatherDataMap;
    }



}
