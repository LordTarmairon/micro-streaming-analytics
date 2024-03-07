package es.amplia.microstreaminganalytics.service;

import es.amplia.microstreaminganalytics.interfaces.IRabbitService;
import es.amplia.microstreaminganalytics.dto.MessageDTO;
import es.amplia.microstreaminganalytics.publisher.RabbitPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitServiceImpl implements IRabbitService {
    @Autowired
    private RabbitPublisher rabbitPublisher;

    @Override
    public void sendToRabbit(MessageDTO message) {
        log.info("Message with ID '{}' will be send...", message.getId());
        rabbitPublisher.send(message);
    }
}