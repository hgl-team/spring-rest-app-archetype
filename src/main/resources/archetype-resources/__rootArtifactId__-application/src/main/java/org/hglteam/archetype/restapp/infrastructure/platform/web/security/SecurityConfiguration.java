#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.hglteam.archetype.restapp.infrastructure.platform.web.security;

import org.hglteam.archetype.restapp.control.security.JwtTokenBasedSessionInspector;
import org.hglteam.archetype.restapp.control.security.SessionInspector;
import org.hglteam.archetype.restapp.infrastructure.platform.config.parameter.AuthorizationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final List<String> clients = Arrays.asList("keycloak", "google");
    private static final String ISSUER_URI_FORMAT = "spring.security.oauth2.client.provider.%s.issuer-uri";
    private static final String CLIENT_ID_FORMAT = "spring.security.oauth2.client.registration.%s.client-id";
    private static final String CLIENT_SECRET_FORMAT = "spring.security.oauth2.client.registration.%s.client-secret";

    private final ApplicationContext context;
    private final AuthorizationParameter authorizationParameter;

    @Autowired
    public SecurityConfiguration(ApplicationContext context, AuthorizationParameter authorizationParameter) {
        this.context = context;
        this.authorizationParameter = authorizationParameter;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = clients.stream()
                .map(this::getRegistration)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(
            ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(
            OAuth2AuthorizedClientService authorizedClientService) {
        return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
    }

    @Bean
    @Scope(scopeName = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public SessionInspector sessionInspector() {
        var token = Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(JwtAuthenticationToken.class::isInstance)
                .map(JwtAuthenticationToken.class::cast)
                .orElseThrow(AuthenticationNotAvailableException::new);
        return new JwtTokenBasedSessionInspector(token);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .logout().logoutSuccessUrl("/home");

        for (var autorizacion : this.authorizationParameter.getPolicies()) {
            switch (autorizacion.getAction()) {
                case ALLOW:
                    http.authorizeRequests()
                            .antMatchers(autorizacion.getUrls().toArray(String[]::new))
                            .permitAll()
                            .and();
                    break;
                case DEMAND_AUTHORITY:
                    if(!autorizacion.getRoles().isEmpty()) {
                        http.authorizeRequests()
                            .antMatchers(autorizacion.getUrls().toArray(String[]::new))
                            .hasAnyAuthority(autorizacion.getRoles().toArray(String[]::new))
                            .and();
                    } else {
                        http.authorizeRequests()
                                .antMatchers(autorizacion.getUrls().toArray(String[]::new))
                                .authenticated()
                                .and();
                    }
                    break;
                case DENY:
                    http.authorizeRequests()
                            .antMatchers(autorizacion.getUrls().toArray(String[]::new))
                            .denyAll()
                            .and();
                    break;
            }
        }

        http.oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(server -> server.jwt(this::configureJwt));
    }

    private void configureJwt(OAuth2ResourceServerConfigurer.JwtConfigurer jwtConfigurer) {
        for (var client : clients) {
            if(!client.matches("google|facebook")) {
                var issuerUri = context.getEnvironment().getProperty(String.format(ISSUER_URI_FORMAT, client));
                assert Objects.nonNull(issuerUri);
                jwtConfigurer.decoder(JwtDecoders.fromOidcIssuerLocation(issuerUri));
            }
        }

        jwtConfigurer.jwtAuthenticationConverter(CustomJwtAuthorityExtractor.fromThisConverter());
    }

    private ClientRegistration getRegistration(String client) {
        var clientId = context.getEnvironment().getProperty(String.format(CLIENT_ID_FORMAT, client));
        var clientSecret = context.getEnvironment().getProperty(String.format(CLIENT_SECRET_FORMAT, client));

        if(clientId == null) {
            return null;
        } else {
            switch (client) {
                case "google":
                    return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                            .clientId(clientId).clientSecret(clientSecret).build();
                case "facebook":
                    return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                            .clientId(clientId).clientSecret(clientSecret).build();
                default:
                    var issuerUri = context.getEnvironment().getProperty(String.format(ISSUER_URI_FORMAT, client));
                    assert Objects.nonNull(issuerUri);
                    return ClientRegistrations.fromOidcIssuerLocation(issuerUri)
                            .clientId(clientId)
                            .clientSecret(clientSecret)
                            .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                            .build();
            }
        }
    }

    public static class AuthenticationNotAvailableException extends IllegalStateException { }
}
