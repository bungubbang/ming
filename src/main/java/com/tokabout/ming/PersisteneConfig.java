package com.tokabout.ming;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.hibernate.cfg.Environment;
import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

/**
 * Created by bungubbang
 * Email: sungyong.jung@sk.com
 * Date: 8/25/13
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.tokabout.ming.module")
public class PersisteneConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource
            , @Value("${hibernate.dialect}") String dialect
            , @Value("${hibernate.hbm2ddl.auto}") String hbm2ddlAuto
            , @Value("${hibernate.show_sql}") String showSql
            , @Value("${hibernate.format_sql}") String formatSql) {

        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        Properties jpaProperties = new Properties();
        jpaProperties.put(Environment.DIALECT, dialect);
        jpaProperties.put(Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        jpaProperties.put(Environment.SHOW_SQL, showSql);
        jpaProperties.put(Environment.FORMAT_SQL, formatSql);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        factoryBean.setJpaProperties(jpaProperties);
        factoryBean.setPackagesToScan("com.tokabout.ming.domain");

        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }


    @Bean
    public DataSource dataSource(@Value("${jdbc.driver}") String driver
            , @Value("${jdbc.url}") String url
            , @Value("${jdbc.user}") String user
            , @Value("${jdbc.password}") String password) throws PropertyVetoException {


        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        return Log4jdbcProxyDataSourceFactory.getLog4jdbcProxyDataSource(dataSource);
    }

    static class Log4jdbcProxyDataSourceFactory {

        static DataSource getLog4jdbcProxyDataSource(DataSource dataSource) {
            Log4JdbcCustomFormatter logFormatter = new Log4JdbcCustomFormatter();
            ConfigurablePropertyAccessor logFormatterAccessor = PropertyAccessorFactory.forDirectFieldAccess(logFormatter);
            logFormatterAccessor.setPropertyValue("loggingType", LoggingType.MULTI_LINE);
            logFormatterAccessor.setPropertyValue("margin", String.format("%1$33s", ""));
            logFormatterAccessor.setPropertyValue("sqlPrefix", "SQL::: ");

            Log4jdbcProxyDataSource log4jdbcProxyDataSource = new Log4jdbcProxyDataSource(dataSource);
            log4jdbcProxyDataSource.setLogFormatter(logFormatter);

            return log4jdbcProxyDataSource;
        }

    }
}
