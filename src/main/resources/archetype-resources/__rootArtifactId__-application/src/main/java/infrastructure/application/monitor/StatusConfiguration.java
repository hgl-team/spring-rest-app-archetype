#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.application.monitor;

import ${package}.business.monitor.StatusBusiness;
import ${package}.control.security.SessionInspector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatusConfiguration {

    @Bean
    public StatusBusiness statusBusiness(SessionInspector sessionInspector) {
        return new StatusBusiness(sessionInspector);
    }
}
