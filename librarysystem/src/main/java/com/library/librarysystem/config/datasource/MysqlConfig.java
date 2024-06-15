package com.library.librarysystem.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@PropertySource({"classpath:application.yml"})
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.library.librarysystem.mysql"},
        entityManagerFactoryRef = "mysqlEM",
        transactionManagerRef = "mysqlTM")
@EntityScan(basePackages = {"com.library.librarysystem.mysql"})
public class MysqlConfig {
    @Autowired
    private Environment env;

    @Value("${spring.datasource.mysql.dll-auto}")
    private String HBM2DDL_AUTO;

    @Value("${spring.datasource.mysql.jpa.show-sql}")
    private String SHOW_SQL;

    @Value("${spring.datasource.mysql.jpa.format-sql}")
    private String FORMAT_SQL;

    @Value("${spring.datasource.mysql.jpa.generate-statistics}")
    private String GENERATE_STATISTICS;


    @Primary
    @Bean(name = "mysqlDSP")
    @ConfigurationProperties(prefix="spring.datasource.mysql")
    public DataSourceProperties mysqlDataSourceProperties(){
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "mysqlDS")
    @ConfigurationProperties(prefix="spring.datasource.mysql.hikari")
    public DataSource mysqlDataSource() {
        return mysqlDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "mysqlEM")
    public LocalContainerEntityManagerFactoryBean mysqlEntityyManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, Object> props = new HashMap<>();
        props.put(AvailableSettings.PHYSICAL_NAMING_STRATEGY, CamelCaseToUnderscoresNamingStrategy.class.getName());
        props.put(AvailableSettings.IMPLICIT_NAMING_STRATEGY, SpringImplicitNamingStrategy.class.getName());
        props.put(AvailableSettings.HBM2DDL_AUTO, HBM2DDL_AUTO);
        props.put(AvailableSettings.DIALECT, MySQLDialect.class.getName());
        props.put(AvailableSettings.SHOW_SQL, SHOW_SQL);
        props.put(AvailableSettings.FORMAT_SQL, FORMAT_SQL);
        props.put(AvailableSettings.GENERATE_STATISTICS, GENERATE_STATISTICS);

        return builder.dataSource(mysqlDataSource())
                .packages("com.library.librarysystem.mysql")
                .properties(props).build();
    }

    @Primary
    @Bean(name = "mysqlTM")
    public PlatformTransactionManager mysqlTransactionManager(@Qualifier("mysqlEM") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactoryBean.getObject()));
    }

}
