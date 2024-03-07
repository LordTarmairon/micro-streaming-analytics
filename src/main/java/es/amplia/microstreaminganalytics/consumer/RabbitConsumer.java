package es.amplia.microstreaminganalytics.consumer;

import es.amplia.microstreaminganalytics.config.PublisherConfig;
import es.amplia.microstreaminganalytics.model.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitConsumer {
    @Autowired
    PublisherConfig publisherConfig;
    @RabbitListener(queues = {"#{publisherConfig.queue}"})
    public void receiver(@Payload CustomMessage message) {
        log.info("Received message {}", message);
        makeSlow();
    }

    private void makeSlow(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
