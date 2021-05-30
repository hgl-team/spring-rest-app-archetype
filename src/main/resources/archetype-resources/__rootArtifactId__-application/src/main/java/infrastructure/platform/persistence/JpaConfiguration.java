#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infrastructure.platform.persistence;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class JpaConfiguration {
    private static Logger log = LoggerFactory.getLogger(JpaConfiguration.class);
    public static final String PERSISTENCE_UNIT_NAME = "applicationPU";
    public static final String ENTITY_MANAGER_FACTORY_JNDI_NAME = "java:/applicationEMF";

    @Bean(PERSISTENCE_UNIT_NAME)
    @Primary
    public FactoryBean<EntityManagerFactory> entityManagerFactory(
            DatasourceParameter datasourceParameter,
            @Qualifier("applicationDatasource") DataSource dataSource) {
        if(datasourceParameter.isJpaProvided()) {
            log.info("Setting up EntityManagerFactory from container.");
            return new JndiEntityManagerFactoryFactoryBean(Collections.singletonList(ENTITY_MANAGER_FACTORY_JNDI_NAME));
        } else {
            log.info("Setting up local container entity manager factory bean");

            var vendorAdapter = new HibernateJpaVendorAdapter();
            var properties = new Properties();
            var factory = new LocalContainerEntityManagerFactoryBean();
            Consumer<DataSource> datasourceSetter = datasourceParameter.isJndi() ?
                    factory::setJtaDataSource : factory::setDataSource;

            vendorAdapter.setGenerateDdl(false);
            datasourceSetter.accept(dataSource);
            factory.setJpaVendorAdapter(vendorAdapter);
            factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
            factory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
            factory.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
            factory.setJpaProperties(properties);
            factory.setPersistenceUnitPostProcessors(this::setResourceLocalTransactionType);

            return factory;
        }
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(
            DatasourceParameter datasourceParameter,
            EntityManagerFactory entityManagerFactory) {
        if(datasourceParameter.isJpaProvided()) {
            log.info("Setting up JTA transaction manager");
            return new JtaTransactionManager();
        } else {
            log.info("Setting up local JPA transaction manager");
            JpaTransactionManager txManager = new JpaTransactionManager();
            txManager.setEntityManagerFactory(entityManagerFactory);
            return txManager;
        }
    }

    private void setResourceLocalTransactionType(MutablePersistenceUnitInfo mutablePersistenceUnitInfo) {
        mutablePersistenceUnitInfo.setTransactionType(PersistenceUnitTransactionType.RESOURCE_LOCAL);
    }

    public static class JndiEntityManagerFactoryFactoryBean implements FactoryBean<EntityManagerFactory> {
        private final Collection<String> jndiNameOptions;

        public JndiEntityManagerFactoryFactoryBean(Collection<String> jndiNameOptions) {
            this.jndiNameOptions = jndiNameOptions;
        }

        @Override
        public EntityManagerFactory getObject() throws Exception {
            return jndiNameOptions.stream()
                    .map(this::doJndiLookup)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Could not find an EntityManagerFactory instance from JNDI."));
        }

        private EntityManagerFactory doJndiLookup(String jndiName) {
            EntityManagerFactory result = null;

            try {
                result = InitialContext.doLookup(ENTITY_MANAGER_FACTORY_JNDI_NAME);
                log.info("Found EntityManagerFactory with JNDI name {}", jndiName);
            } catch (NamingException e) {
                log.info("Failed to find EntityManagerFactory with JNDI name {}. Skip.", jndiName);
            }

            return result;
        }

        @Override
        public Class<?> getObjectType() {
            return EntityManagerFactory.class;
        }
    }
}
