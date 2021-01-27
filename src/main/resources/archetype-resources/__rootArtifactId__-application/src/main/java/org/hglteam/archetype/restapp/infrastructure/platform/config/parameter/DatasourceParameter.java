#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.hglteam.archetype.restapp.infrastructure.platform.config.parameter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
public class DatasourceParameter implements Serializable {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String jndiName;
    private boolean isJndi;

    @Override
    public String toString() {
        return "DataSourceInformation{" +
                "driverClassName='" + driverClassName + '${symbol_escape}'' +
                ", url='" + url + '${symbol_escape}'' +
                ", username='" + username + '${symbol_escape}'' +
                ", password='" + password + '${symbol_escape}'' +
                ", jndiName='" + jndiName + '${symbol_escape}'' +
                ", isJndi=" + isJndi +
                '}';
    }
}
