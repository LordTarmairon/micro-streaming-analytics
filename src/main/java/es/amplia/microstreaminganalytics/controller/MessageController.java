package es.amplia.microstreaminganalytics.controller;

import es.amplia.microstreaminganalytics.interfaces.IRabbitService;
import es.amplia.microstreaminganalytics.model.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class MessageController {
    @Autowired
    private IRabbitService iRabbitService;

    @PostMapping("/test")
    public void testSendMessage(@RequestBody CustomMessage message) {
        message.setId((long)(Math.random() * Long.MAX_VALUE));
        message.setDate(new Date());
        iRabbitService.sendToRabbit(message);
    }
}
