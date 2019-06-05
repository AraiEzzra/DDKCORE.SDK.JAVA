package global.eska.ddk.keygen.demo;

import global.eska.ddk.DDKBeanConfiguration;
import global.eska.ddk.DDKConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;

@ConfigurationProperties(prefix = "ddk")
@Import({DDKConfiguration.class, DDKBeanConfiguration.class})
public class DemoApplicationConfig {

}
