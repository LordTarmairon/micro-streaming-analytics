package es.amplia.microstreaminganalytics.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@ConfigurationProperties(prefix = "micro.streaming.analytics.config", ignoreUnknownFields = false)
@Configuration
public class BatchProperties {
    @Setter
    private String version;

}