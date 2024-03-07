package es.amplia.microstreaminganalytics.interfaces;

import es.amplia.microstreaminganalytics.model.CustomMessage;

public interface IRabbitService {
    void sendToRabbit(CustomMessage message);
}
