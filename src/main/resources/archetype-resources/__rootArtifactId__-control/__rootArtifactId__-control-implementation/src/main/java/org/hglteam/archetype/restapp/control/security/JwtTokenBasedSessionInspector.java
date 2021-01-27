#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.hglteam.archetype.restapp.control.security;

import org.hglteam.archetype.restapp.model.domain.SessionUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class JwtTokenBasedSessionInspector implements SessionInspector {
    private final JwtAuthenticationToken token;
    private SessionUser sessionUserCache;

    public JwtTokenBasedSessionInspector(JwtAuthenticationToken token) {
        this.token = token;
    }

    @Override
    public SessionUser getUser() {
        this.sessionUserCache = Optional.ofNullable(this.sessionUserCache)
                .orElse(SessionUser.builder()
                        .userId(this.getUserId())
                        .username(this.getPreferredUserName())
                        .email(this.getEmail())
                        .roles(this.getRealmAccess())
                        .build());

        return this.sessionUserCache;
    }

    @Override
    public String getToken() {
        return this.token.getToken().getTokenValue();
    }

    private String getEmail() {
        return JwtTokenBasedSessionInspector.getClaim(this.token.getToken(), "email")
                .map(Objects::toString)
                .orElseThrow(IllegalArgumentException::new);
    }

    private String getPreferredUserName() {
        return JwtTokenBasedSessionInspector.getClaim(this.token.getToken(), "preferred_username")
                .map(Objects::toString)
                .orElseThrow(IllegalArgumentException::new);
    }

    private String getUserId() {
        return JwtTokenBasedSessionInspector.getClaim(this.token.getToken(), "sub")
                .map(Objects::toString)
                .orElseThrow(IllegalArgumentException::new);
    }

    private List<String> getRealmAccess() {
        return JwtTokenBasedSessionInspector.getClaim(this.token.getToken(), "realm_access", "roles")
                .filter(List.class::isInstance)
                .map(roles -> ((List<?>)roles).stream()
                        .map(Object::toString)
                        .collect(Collectors.toUnmodifiableList()))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Optional<Object> getClaim(Jwt token, String... path) {
        var current = Optional.<Object>ofNullable(token.getClaims());

        for (var node : path) {
            current = current.filter(Map.class::isInstance)
                    .map(Map.class::cast)
                    .map(map -> map.get(node));
        }

        return current;
    }
}
