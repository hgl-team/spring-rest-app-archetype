#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SessionUser {
    private String userId;
    private String username;
    private String email;
    private List<String> roles;

    @Override
    public String toString() {
        return "SessionUser{" +
                "userId='" + userId + '${symbol_escape}'' +
                ", username='" + username + '${symbol_escape}'' +
                ", email='" + email + '${symbol_escape}'' +
                ", roles={" + String.join(", ", roles) + "}" +
                '}';
    }
}
