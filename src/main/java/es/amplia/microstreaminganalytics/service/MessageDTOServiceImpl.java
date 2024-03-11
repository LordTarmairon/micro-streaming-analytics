package es.amplia.microstreaminganalytics.service;

import es.amplia.microstreaminganalytics.dto.MessageDTO;
import es.amplia.microstreaminganalytics.interfaces.IMessageDTOService;
import es.amplia.microstreaminganalytics.model.Statistics;
import es.amplia.microstreaminganalytics.repository.MessageDTORepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Slf4j
public class MessageDTOServiceImpl implements IMessageDTOService {
    @Autowired
    MessageDTORepository messageDTORepository;

    @Override
    public MessageDTO getMessageDTOByID(Long id) {
        log.info("Finding Course by ID: {}",id);
        return messageDTORepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("getMessageDTOByID, it gets an error finding a MessageDTO by ID: "+ id));
    }
    @Override
    public List<MessageDTO> getAllMessageDTO() {
        log.info("Getting all MessageDTOs");
        return messageDTORepository.findAll();
    }

    @Override
    @Transactional
    public MessageDTO saveProduct(MessageDTO messageDTO) {
        try {
            log.info("saveProduct, Saving MessageDTO with ID: {}", messageDTO.getId());
            return messageDTORepository.save(messageDTO);
        } catch (DataAccessException e) {
            log.error("saveProduct, Error saving MessageDTO with ID: {}. {} ",messageDTO.getId(), e.getMessage());
            throw new RuntimeException("Error saving MessageDTO: " + e.getMessage(), e);
        }
    }
}
