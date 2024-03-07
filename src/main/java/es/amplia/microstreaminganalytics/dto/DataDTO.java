package es.amplia.microstreaminganalytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataDTO {

    private double temperature;
    private double humidity;
    private double pressure;
    private double windSpeed;

}
