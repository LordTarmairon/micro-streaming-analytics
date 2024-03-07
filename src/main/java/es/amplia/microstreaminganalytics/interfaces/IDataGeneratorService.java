package es.amplia.microstreaminganalytics.interfaces;

import es.amplia.microstreaminganalytics.dto.MessageDTO;

import java.util.List;

public interface IDataGeneratorService {
    MessageDTO generateNewMessageDTO();

    List<MessageDTO> generateNewMessageDTO(int quantity);
}
