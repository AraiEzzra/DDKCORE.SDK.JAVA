package global.eska.ddk.api.client.model;


import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseEntity {

    private Boolean success;
    private List<String> errors;
    private Message message;

}
