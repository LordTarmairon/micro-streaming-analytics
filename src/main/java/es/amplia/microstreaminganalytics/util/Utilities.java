package es.amplia.microstreaminganalytics.util;

import es.amplia.microstreaminganalytics.dto.MessageDTO;
import es.amplia.microstreaminganalytics.model.Statistics;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utilities {
    public static double[] convertToArray(List<Double> list) {
        if (list == null) throw new IllegalArgumentException("List cannot be null");
        if (list.isEmpty()) throw new IllegalArgumentException("List cannot be empty");

        double[] doubleArray = new double[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Double value = list.get(i);
            if (value == null) {
                throw new IllegalArgumentException("List cannot have null values");
            }
            doubleArray[i] = value;
        }
        return doubleArray;
    }

    public static Statistics getStatisticsObject(EntityName entityName, List<Double> values) {
        Statistics statistics = new Statistics();
        statistics.setDate(new Date());
        statistics.setId((long)(Math.random() * Long.MAX_VALUE));
        switch (entityName) {
            case TEMPERATURE:
                statistics.setEntityName(entityName);
                statistics.calculateStatistics(convertToArray(values));
                break;
            case HUMIDITY:
                statistics.setEntityName(entityName);
                statistics.calculateStatistics(convertToArray(values));
                break;
            case PRESSURE:
                statistics.setEntityName(entityName);
                statistics.calculateStatistics(convertToArray(values));
                break;
            case WINDSPEED:
                statistics.setEntityName(entityName);
                statistics.calculateStatistics(convertToArray(values));
                break;
            default:
                throw new EntityNotFoundException("Entity Name not valid or not found");
        }

        return statistics;
    }

}
