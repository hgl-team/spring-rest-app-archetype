#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.hglteam.archetype.restapp.business.monitor;

import org.hglteam.archetype.restapp.control.security.SessionInspector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusBusiness {
    private static final Logger log = LoggerFactory.getLogger(StatusBusiness.class);
    private final SessionInspector sessionInspector;


    public StatusBusiness(SessionInspector sessionInspector) {
        this.sessionInspector = sessionInspector;
    }

    public void ping() {
        log.info("Ping: OK");
    }

    public void securedPin() {
        var user = sessionInspector.getUser();
        log.info("User: {}", user);
        log.info("Ping: OK");
    }

}
