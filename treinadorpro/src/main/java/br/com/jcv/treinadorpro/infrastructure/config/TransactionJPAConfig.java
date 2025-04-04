package br.com.jcv.treinadorpro.infrastructure.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class TransactionJPAConfig{
   private static final Logger log = LoggerFactory.getLogger(TransactionJPAConfig.class);

   @Autowired private TreinadorProConfig config;
   @Bean
   public DataSource dataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();

      dataSource.setDriverClassName(config.getDbDriverClassName());
      dataSource.setUsername(config.getDbUsername());
      dataSource.setPassword(config.getDbPassword());
      dataSource.setUrl(config.getDbUrl());
      log.info("dataSource Bean :: has been created");
      return dataSource;
   }

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean em
              = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource());
      em.setPackagesToScan("br.com.jcv.treinadorpro.corelayer.model");

      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      em.setJpaProperties(additionalProperties());

      log.info("entityManagerFactory Bean :: has been created");
      return em;
   }

   @Bean(name = "transactionManager")
   public PlatformTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
      log.info("transactionManager Bean :: has been created successfully");
      return transactionManager;
   }

   private Properties additionalProperties() {
      return null;
   }
}
