package es.amplia.microstreaminganalytics.interfaces;

import es.amplia.microstreaminganalytics.dto.MessageDTO;

public interface IRabbitService {
    void sendToRabbit(MessageDTO message);
}
