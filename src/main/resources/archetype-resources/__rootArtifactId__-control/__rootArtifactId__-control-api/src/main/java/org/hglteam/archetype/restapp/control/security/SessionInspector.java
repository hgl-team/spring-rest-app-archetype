#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.hglteam.archetype.restapp.control.security;

import org.hglteam.archetype.restapp.model.domain.SessionUser;

public interface SessionInspector {
    SessionUser getUser();
    String getToken();
}
