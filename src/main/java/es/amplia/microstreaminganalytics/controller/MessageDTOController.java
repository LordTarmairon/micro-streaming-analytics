package es.amplia.microstreaminganalytics.controller;

import es.amplia.microstreaminganalytics.dto.MessageDTO;
import es.amplia.microstreaminganalytics.interfaces.IMessageDTOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api")
public class MessageDTOController {
    @Autowired
    IMessageDTOService iMessageDTOService;

    @PostMapping("/getAllMessageDTOs")
    public ResponseEntity<List<MessageDTO>> getAllMessageDTOs () {
        List<MessageDTO> messageDTOS;

        messageDTOS = iMessageDTOService.getAllMessageDTO();
        if(messageDTOS.isEmpty()){
            log.error("An error occurred getting all messageDTOs, List size: {}", messageDTOS.size());
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(messageDTOS);
    }
}
