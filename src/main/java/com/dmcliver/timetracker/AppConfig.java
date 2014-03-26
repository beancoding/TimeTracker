package com.dmcliver.timetracker;


import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class AppConfig {

	@Bean
	public JpaTransactionManager transactionManager() throws PropertyVetoException{
	
		JpaTransactionManager tx = new JpaTransactionManager();
		tx.setEntityManagerFactory(entityManagerFactory().getObject());
		return tx;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException {
		
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource());
		emf.setPackagesToScan("com.dmcliver.timetracker.domain");
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setJpaProperties(jpaProperties());
		return emf;
	}

	private Properties jpaProperties() {
		
		Properties props = new Properties();
		props.put("hibernate.show_sql", true);
		return props;
	}

	@Bean
	public DataSource dataSource() throws PropertyVetoException {
		
		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setUser("Timetracker");
		ds.setPassword("root");
		ds.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		ds.setDriverClass(oracle.jdbc.OracleDriver.class.getName());
		return ds;
	}
}
