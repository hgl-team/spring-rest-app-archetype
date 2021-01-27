#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.platform.config;

import ${package}.infrastructure.platform.YAMLPropertySourceFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

public class PlatformConfigurationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        var resource = new ClassPathResource("bootstrap.yml");
        var r = new EncodedResource(resource);

        try {
            applicationContext.getEnvironment()
                    .getPropertySources()
                    .addFirst(new YAMLPropertySourceFactory().createPropertySource("bootstrap", r));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
