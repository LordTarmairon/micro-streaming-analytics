package es.amplia.microstreaminganalytics.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "micro.streaming.analytics.config", ignoreUnknownFields = false)
@Configuration
public class BatchProperties {

    private String version;

    private List<String> entityNames;

}