#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.hglteam.archetype.restapp.infrastructure.platform.web.security;

import org.hglteam.archetype.restapp.control.security.JwtTokenBasedSessionInspector;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CustomJwtAuthorityExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return JwtTokenBasedSessionInspector.getClaim(jwt, "realm_access", "roles")
                .filter(Collection.class::isInstance)
                .map(value -> (Collection<?>)value)
                .map(list -> list.stream()
                        .map(Object::toString)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toCollection((Supplier<ArrayList<GrantedAuthority>>) ArrayList::new)))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static Converter<Jwt, AbstractAuthenticationToken> fromThisConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new CustomJwtAuthorityExtractor());

        return jwtAuthenticationConverter;
    }
}
