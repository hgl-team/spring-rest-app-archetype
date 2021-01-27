#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.control.security;

import ${package}.model.domain.SessionUser;

public interface SessionInspector {
    SessionUser getUser();
    String getToken();
}
