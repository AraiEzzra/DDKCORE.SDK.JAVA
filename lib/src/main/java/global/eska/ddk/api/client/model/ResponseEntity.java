package global.eska.ddk.api.client.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class ResponseEntity {

    private Boolean success;
    private String[] errors;
    private Message message;

}
