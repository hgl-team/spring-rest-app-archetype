#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.platform.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ${package}.infrastructure.platform.config.parameter.AuthorizationParameter;
import ${package}.infrastructure.platform.config.parameter.CorsParameter;
import ${package}.infrastructure.platform.config.parameter.DatasourceParameter;
import ${package}.infrastructure.platform.config.parameter.LocalizationParameter;
import ${package}.infrastructure.platform.web.security.CorsMapping;
import ${package}.infrastructure.platform.web.security.AuthorizationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.List;

@Configuration
public class ParameterConfiguration {
    private static final Logger log = LoggerFactory.getLogger(ParameterConfiguration.class);

    @Bean
    public DatasourceParameter datasourceParameter(
            @Value("${symbol_dollar}{application.datasource.driver}") String driverClassName,
            @Value("${symbol_dollar}{application.datasource.url}") String url,
            @Value("${symbol_dollar}{application.datasource.username}") String username,
            @Value("${symbol_dollar}{application.datasource.password}") String password,
            @Value("${symbol_dollar}{application.datasource.is-jndi}") Boolean isJndi,
            @Value("${symbol_dollar}{application.datasource.jndi-name}") String jndiName) {
        return DatasourceParameter.builder()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .isJndi(Boolean.TRUE.equals(isJndi))
                .jndiName(jndiName)
                .build();
    }

    @Bean
    public AuthorizationParameter authorizationParameter(
            @Value("${symbol_dollar}{application.security.authorizations}") String autorizations,
            Gson gson) throws Exception {
        log.debug("Authorization Policies: {}", autorizations);

        return AuthorizationParameter.builder()
                .policies(gson.fromJson(autorizations, new TypeToken<List<AuthorizationPolicy>>(){}.getType()))
                .build();
    }

    @Bean
    public CorsParameter corsParameter(
            @Value("${symbol_dollar}{application.security.cors}") String corsConfig,
            Gson gson) {
        log.debug("Cors configuration: {}", corsConfig);

        return CorsParameter.builder()
                .mappings(gson.fromJson(corsConfig, new TypeToken<List<CorsMapping>>(){}.getType()))
                .build();
    }

    @Bean
    public LocalizationParameter localizationParameter(
            @Value("${symbol_dollar}{application.localization.time.zone-id}") String zoneId,
            @Value("${symbol_dollar}{application.json.format.local-date}") String localDateFormat,
            @Value("${symbol_dollar}{application.json.format.local-time}") String localTimeFormat,
            @Value("${symbol_dollar}{application.json.format.local-datetime}") String localDateTimeFormat) {
        return LocalizationParameter.builder()
                .zoneId(ZoneId.of(zoneId))
                .localDateFormat(localDateFormat)
                .localTimeFormat(localTimeFormat)
                .localDateTimeFormat(localDateTimeFormat)
                .build();
    }
}
