package global.eska.ddk;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "ddk")
public class DDKConfiguration {

    private String protocol;

    private String host;

    private int port;

    public String getUrl() {
        return String.format("%s://%s:%s", protocol, host, port);
    }
}
