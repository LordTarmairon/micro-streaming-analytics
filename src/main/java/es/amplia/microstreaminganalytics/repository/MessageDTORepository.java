package es.amplia.microstreaminganalytics.repository;

import es.amplia.microstreaminganalytics.dto.MessageDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface MessageDTORepository extends MongoRepository<MessageDTO, Long> {

}
