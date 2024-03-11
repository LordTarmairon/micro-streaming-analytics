package es.amplia.microstreaminganalytics.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "MessageDTO")
public class MessageDTO {

    @Id
    private long id;
    private String version;
    private Date date;
    private String entityId;
    private DataDTO data;

}
