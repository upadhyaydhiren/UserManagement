package com.papoye.UserMangement.util;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.papoye.UserMangement.domain.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * This is DB Java Configuration that configure all database related
 * configuration and parameters.
 * 
 * @author Dhiren
 * @since 29-01-2016
 */
@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:Hibernate.properties", "classpath:DB.properties" })
public class DBConfig {

	@Value("${hibernate.dialect}")
	private String hibernateDialect;
	@Value("${hibernate.hbm2ddl.auto}")
	private String hibernateDddl;
	@Value("${hibernate.format_sql}")
	private String hibernateFormatSql;
	@Value("${hibernate.show_sql}")
	private String hibernateShowSql;
	@Value("${db.classDriver}")
	private String dbDriverClass;
	@Value("${db.jdbcurl}")
	private String jdbcUrl;
	@Value("${db.username}")
	private String dbUser;
	@Value("${db.password}")
	private String dbPassword;

	@Bean
	public DataSource dataSource() {
		Properties properties = new Properties();
		properties.setProperty("dataSourceClassName", dbDriverClass);
		properties.setProperty("dataSource.url", jdbcUrl);
		properties.setProperty("dataSource.user", dbUser);
		properties.setProperty("dataSource.password", dbPassword);
		HikariConfig hikariConfig = new HikariConfig(properties);
		return new HikariDataSource(hikariConfig);
	}

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(
				dataSource());
		builder.addAnnotatedClass(User.class).addProperties(
				getHibernateProperties());
		return builder.buildSessionFactory();

	}

	public Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.format_sql", hibernateFormatSql);
		properties.put("hibernate.show_sql", hibernateShowSql);
		properties.put("hibernate.dialect", hibernateDialect);
		properties.put("hibernate.hbm2ddl.auto", hibernateDddl);
		return properties;
	}

	@Bean
	public HibernateTransactionManager transactionManager() {
		return new HibernateTransactionManager(sessionFactory());
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
