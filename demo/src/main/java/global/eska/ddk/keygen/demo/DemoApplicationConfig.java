package global.eska.ddk.keygen.demo;

import global.eska.ddk.DDKClientConfiguration;
import global.eska.ddk.DDKSocketClientConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;

@ConfigurationProperties(prefix = "ddk")
@Import({DDKSocketClientConfiguration.class, DDKClientConfiguration.class})
public class DemoApplicationConfig {

}
