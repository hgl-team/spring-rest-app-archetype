#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.hglteam.archetype.restapp.infrastructure.platform.config.parameter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hglteam.archetype.restapp.infrastructure.platform.web.security.CorsMapping;

import java.util.List;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class CorsParameter {
    private List<CorsMapping> mappings;
}
