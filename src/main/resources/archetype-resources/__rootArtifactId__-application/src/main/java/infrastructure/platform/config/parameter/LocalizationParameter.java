#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.platform.config.parameter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZoneId;

@Getter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class LocalizationParameter {
    private ZoneId zoneId;
    private String localDateFormat;
    private String localTimeFormat;
    private String localDateTimeFormat;
}
