package es.amplia.microstreaminganalytics.dto;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private long id;
    private String version;
    private Date date;
    private String entityId;
    private DataDTO data;

}
