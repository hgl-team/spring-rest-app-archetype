#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.platform.config.parameter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ${package}.infrastructure.platform.web.security.AuthorizationPolicy;

import java.util.List;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class AuthorizationParameter {
    private List<AuthorizationPolicy> policies;
}
