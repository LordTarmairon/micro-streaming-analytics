package es.amplia.microstreaminganalytics.interfaces;

import es.amplia.microstreaminganalytics.dto.MessageDTO;

import java.util.List;

public interface IMessageDTOService {
    MessageDTO getMessageDTOByID(Long id);

    List<MessageDTO> getAllMessageDTO();

    MessageDTO saveProduct(MessageDTO messageDTO);
}
