package com.laeith.infrastructure.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan("com.laeith")
@Profile({SpringProfile.DEV, SpringProfile.TEST})
public class TestDevPersistenceConfiguration {

  private static final Logger LOG = LogManager.getLogger(TestDevPersistenceConfiguration.class);

  private static final List<String> PRELOADED_SCRIPTS = List.of(
     "classpath:HSQL/create_tables.sql",
     "classpath:HSQL/test_data.sql"
  );

  public TestDevPersistenceConfiguration() {
    LOG.info("Test/Dev persistence configuration was loaded - INTENDED ONLY FOR DEV/TESTING");
  }

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
    PRELOADED_SCRIPTS.forEach(embeddedDatabaseBuilder::addScript);
    embeddedDatabaseBuilder.setType(EmbeddedDatabaseType.HSQL);
    embeddedDatabaseBuilder.setName("TestDatabase");
    return embeddedDatabaseBuilder.build();
  }

  @SuppressWarnings("Duplicates")
  @Bean
  public LocalContainerEntityManagerFactoryBean emf() {
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource());
    emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    emf.setPackagesToScan("com.laeith");

    Properties jpaProperties = new Properties();
    jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
    jpaProperties.setProperty("hibernate.max_fetch_depth", "3");
    jpaProperties.setProperty("hibernate.jdbc.fetch_size", "50");
    jpaProperties.setProperty("hibernate.jdbc.batch_size", "10");
    jpaProperties.setProperty("hibernate.show_sql", "false");
    jpaProperties.setProperty("hibernate.format_sql", "true");
    jpaProperties.setProperty("hibernate.jdbc.time_zone", "UTC");

    emf.setJpaProperties(jpaProperties);
    return emf;
  }

  @Bean(name = "transactionManager")
  public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(emf);
    jpaTransactionManager.setDataSource(dataSource());

    return new JpaTransactionManager(emf);
  }
}

