package es.amplia.microstreaminganalytics.service;

import es.amplia.microstreaminganalytics.config.BatchProperties;
import es.amplia.microstreaminganalytics.dto.DataDTO;
import es.amplia.microstreaminganalytics.dto.MessageDTO;
import es.amplia.microstreaminganalytics.interfaces.IDataGeneratorService;
import es.amplia.microstreaminganalytics.interfaces.IDataGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class DataGeneratorServiceImpl implements IDataGeneratorService {
    @Autowired
    BatchProperties batchProperties;
    @Autowired
    IDataGenerator iWeatherDataGenerator;

    @Override
    public MessageDTO generateNewMessageDTO () {
        MessageDTO messageDTO;
        try {
            messageDTO = new MessageDTO(
                    (long)(Math.random() * Long.MAX_VALUE),
                    batchProperties.getVersion(),
                    new Date(),
                    "TEST_ENTITY",
                    new DataDTO(
                            iWeatherDataGenerator.generateTemperature(),
                            iWeatherDataGenerator.generateHumidity(),
                            iWeatherDataGenerator.generatePressure(),
                            iWeatherDataGenerator.generateWindSpeed()
                    )

            );
            log.info("generateNewMessageDTO, has generated a new messageDTO with ID: '{}'", messageDTO.getId());

        } catch (Exception e) {
            messageDTO = null;
            e.printStackTrace();
            log.error("generateNewMessageDTO, an error occurred while trying to generate a new messageDTO, {}", e.getMessage());
        }

        return messageDTO;
    }

    @Override
    public List<MessageDTO> generateNewMessageDTO (int quantity) {

        if(quantity > 100) {
            log.warn("generateNewMessageDTO, quantity is bigger than 100, quantity = {}. The maximum number allowed to generate data is 100, so only 100 will be processed.", quantity);
            quantity = 100;
        }

        List<MessageDTO> messageList = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            messageList.add(this.generateNewMessageDTO());
        }

        return messageList;
    }
    
}
