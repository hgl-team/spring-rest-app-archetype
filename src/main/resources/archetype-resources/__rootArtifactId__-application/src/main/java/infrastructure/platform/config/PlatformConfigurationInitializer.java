#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.platform.config;

import ${package}.infrastructure.platform.YAMLPropertySourceFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

public class PlatformConfigurationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        var bootstrapPropertySource = registerBootstrapPropertySource(applicationContext);
        registerBootstrapPropertySourceConfigurer(applicationContext, bootstrapPropertySource);
    }

    private PropertySource<?> registerBootstrapPropertySource(ConfigurableApplicationContext applicationContext) {
        var resource = new ClassPathResource("bootstrap.yml");
        var r = new EncodedResource(resource);

        try {
            var bootstrapPropertySource = new YAMLPropertySourceFactory()
                    .createPropertySource("bootstrap", r);

            applicationContext.getEnvironment()
                    .getPropertySources()
                    .addFirst(bootstrapPropertySource);

            return bootstrapPropertySource;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void registerBootstrapPropertySourceConfigurer(
            ConfigurableApplicationContext applicationContext,
            PropertySource<?> bootstrapPropertySource) {
        var configurer = new PropertySourcesPlaceholderConfigurer();
        var properties = new MutablePropertySources();

        properties.addFirst(bootstrapPropertySource);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setPropertySources(properties);
        applicationContext.addBeanFactoryPostProcessor(configurer);
    }
}
