#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package org.hglteam.archetype.restapp.infrastructure.platform.persistence;

import org.hglteam.archetype.restapp.infrastructure.platform.config.parameter.DatasourceParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DatasourceConfiguration.class);

    @Bean("applicationDatasource")
    public DataSource dataSource (DatasourceParameter datasourceParameter) throws NamingException {
        log.info("Datasource initialized: {}", datasourceParameter);

        if(datasourceParameter.isJndi()) {
            return (DataSource) InitialContext.doLookup(
                    datasourceParameter.getJndiName());
        } else {
            return createDataSource(datasourceParameter);
        }
    }

    public DataSource createDataSource(DatasourceParameter datasourceParameter) {
        DriverManagerDataSource datasource = new DriverManagerDataSource();

        datasource.setDriverClassName(datasourceParameter.getDriverClassName());
        datasource.setUrl(datasourceParameter.getUrl());
        datasource.setUsername(datasourceParameter.getUsername());
        datasource.setPassword(datasourceParameter.getPassword());

        return datasource;
    }
}
