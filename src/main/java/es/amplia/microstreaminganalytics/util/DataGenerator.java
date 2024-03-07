package es.amplia.microstreaminganalytics.util;

import es.amplia.microstreaminganalytics.interfaces.IDataGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class DataGenerator implements IDataGenerator {
    private static final Random random = new Random();

    // Generates a random temperature between -3ºC and 35 with one decimal
    @Override
    public double generateTemperature (){
        return Math.round((-3 + random.nextDouble() * 39) * 10.0) / 10.0;

    }

    // Generates a random humidity level between 30% and 99%
    @Override
    public double generateHumidity () {
        return Math.round((30 + random.nextDouble() * 69) * 10.0) / 10.0;

    }

    // Generates a random atmospheric pressure between 980 hPa and 1020 hPa
    @Override
    public double generatePressure() {
        return Math.round((980 + random.nextDouble() * 40 ) * 100.0) / 100.0;

    }

    // Generates a random wind speed between 0 km/h and 40 km/h
    @Override
    public double generateWindSpeed() {
        return Math.round(random.nextDouble() * 40 );

    }

}
