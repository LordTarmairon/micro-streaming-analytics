package es.amplia.microstreaminganalytics.model;

import es.amplia.microstreaminganalytics.util.EntityName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "statistics")
public class Statistics {
    @Id
    private long id;
    private EntityName entityName;
    private Date date;
    private double mean;
    private double median;
    private double mode;
    private double standardDeviation;
    private double[] quartiles;
    private double maxValue;
    private double minValue;
    private int evaluatedData;

    public Statistics(long id, Date date) {
        this.id = id;
        this.date = date;
    }


    /**
     * This function set statistics data, from array of values.
     * @param values double[]
     */
    public void calculateStatistics(double[] values) {
        DescriptiveStatistics stats = new DescriptiveStatistics(values);

        this.mean = stats.getMean();
        this.median = stats.getPercentile(50);
        this.mode = this.calculateMode(values);
        this.standardDeviation = stats.getStandardDeviation();
        this.quartiles = new double[]{stats.getPercentile(25), stats.getPercentile(50), stats.getPercentile(75)};
        this.maxValue = stats.getMax();
        this.minValue = stats.getMin();
        this.evaluatedData = values.length;
    }

    /**
     * This function get mode from array of values.
     * @param values double[]
     * @return mode double
     */

    public double calculateMode(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Values array, cannot be null or empty");
        }

        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double value : values) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }

        double mode = Double.NaN;
        int maxFrequency = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mode = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }

        return mode;
    }
}
