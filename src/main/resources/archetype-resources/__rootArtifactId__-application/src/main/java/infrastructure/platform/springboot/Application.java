#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.platform.springboot;

import ${package}.infrastructure.platform.config.PlatformConfigurationInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "${package}.web.rest",
        "${package}.infrastructure",
})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        var app = new SpringApplication(Application.class);
        app.addInitializers(new PlatformConfigurationInitializer());
        app.run(args);
    }
}
