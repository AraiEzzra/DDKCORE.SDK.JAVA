package global.eska.ddk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "ddk")
public class DDKSocketClientConfiguration {

    private String protocol;

    private String host;

    private int port;

    public String getUrl() {
        return String.format("%s://%s:%s", protocol, host, port);
    }
}
