package global.eska.ddk.api.client.model.socket;


import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity {

    private Boolean success;
    private List<String> errors;
    private Object data;

}
