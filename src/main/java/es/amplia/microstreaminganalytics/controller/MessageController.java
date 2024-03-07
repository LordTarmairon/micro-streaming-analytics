package es.amplia.microstreaminganalytics.controller;

import es.amplia.microstreaminganalytics.config.BatchProperties;
import es.amplia.microstreaminganalytics.interfaces.IDataGeneratorService;
import es.amplia.microstreaminganalytics.interfaces.IRabbitService;
import es.amplia.microstreaminganalytics.dto.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class MessageController {
    @Autowired
    private IRabbitService iRabbitService;
    @Autowired
    private BatchProperties batchProperties;

    @Autowired
    private IDataGeneratorService iDataGeneratorService;

    @PostMapping("/test")
    public void testSendMessage() {
        iRabbitService.sendToRabbit(iDataGeneratorService.generateNewMessageDTO());
    }

    @PostMapping("/sendRandom")
    public ResponseEntity<String> testSendMessage(@RequestBody int quantity) {
        List<MessageDTO> messageDTOs;
        if(quantity <= 0) {
            log.error("Sent value is less than 0, value: '{}'", quantity);
            return ResponseEntity.badRequest().body("Value must be bigger than 0.");
        }
        messageDTOs = iDataGeneratorService.generateNewMessageDTO(quantity);
        messageDTOs.forEach(messageDTO -> {
            iRabbitService.sendToRabbit(iDataGeneratorService.generateNewMessageDTO());
        });

        return ResponseEntity.ok("Message was sent");
    }
    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok("V ".concat(batchProperties.getVersion()));

    }

}
