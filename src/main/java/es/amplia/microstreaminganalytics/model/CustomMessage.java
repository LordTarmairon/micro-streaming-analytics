package es.amplia.microstreaminganalytics.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private long id;
    private Date date;
    private String message;

}
