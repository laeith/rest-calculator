package com.laeith.infrastructure.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@Profile(SpringProfile.PRODUCTION)
public class PersistenceConfiguration {

  private static final Logger LOG = LogManager.getLogger(PersistenceConfiguration.class);

  private final Environment environment;

  public PersistenceConfiguration(Environment environment) {
    this.environment = environment;
    LOG.info("Production persistence config was loaded.");
  }

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("org.postgresql.Driver");
    ds.setUrl(environment.getProperty("database.url"));
    ds.setUsername(environment.getProperty("database.username"));
    ds.setPassword(environment.getProperty("database.password"));
    return ds;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean emf() {
    LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    emf.setDataSource(dataSource());
    emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    emf.setPackagesToScan("com.laeith");

    Properties jpaProperties = new Properties();
    jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL82Dialect");
    jpaProperties.setProperty("hibernate.max_fetch_depth", "3");
    jpaProperties.setProperty("hibernate.jdbc.fetch_size", "50");
    jpaProperties.setProperty("hibernate.jdbc.batch_size", "10");
    jpaProperties.setProperty("hibernate.show_sql", "false");
    jpaProperties.setProperty("hibernate.format_sql", "false");

    emf.setJpaProperties(jpaProperties);
    return emf;
  }

  @Bean(name = "transactionManager")
  public JpaTransactionManager txManager(EntityManagerFactory emf) {
    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(emf);
    jpaTransactionManager.setDataSource(dataSource());

    return new JpaTransactionManager(emf);
  }

}

