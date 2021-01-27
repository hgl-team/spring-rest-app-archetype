#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.hglteam.archetype.restapp.infrastructure.platform.springboot;

import org.hglteam.archetype.restapp.infrastructure.platform.config.PlatformConfigurationInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "org.hglteam.archetype.restapp.web.rest",
        "org.hglteam.archetype.restapp.infrastructure",
})
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        var app = new SpringApplication(Application.class);
        app.addInitializers(new PlatformConfigurationInitializer());
        app.run(args);
    }
}
