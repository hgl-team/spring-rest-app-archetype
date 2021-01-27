#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.hglteam.archetype.restapp.infrastructure.application.monitor;

import org.hglteam.archetype.restapp.business.monitor.StatusBusiness;
import org.hglteam.archetype.restapp.control.security.SessionInspector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatusConfiguration {

    @Bean
    public StatusBusiness statusBusiness(SessionInspector sessionInspector) {
        return new StatusBusiness(sessionInspector);
    }
}
