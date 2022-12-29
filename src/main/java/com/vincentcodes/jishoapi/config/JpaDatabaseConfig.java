package com.vincentcodes.jishoapi.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Database Config.
 * <p>
 * Database schema setup is all done separately.
 *
 * <p>
 * Authorization Strategies:
 * Method 1. Associate a session with user (definition).
 * Instead of creating a LoggedInSession Table in the database, we will just
 * use SESSION_ATTRIBUTE provided by Spring Session already and set auth=true
 *
 * Method 2. Use API Keys. Impl is similar to storing sessions? OR use hashing
 * To get started, read these...
 * Ref: https://stackoverflow.com/questions/48446708/securing-spring-boot-api-with-api-key-and-secret
 * https://security.stackexchange.com/questions/18684/how-to-implement-an-api-key-mechanism
 *
 * Current Session Strategy: Method 1
 */
@Configuration
//@EnableJdbcHttpSession // [optional] not needed if "spring.session.store-type: jdbc" exists
public class JpaDatabaseConfig {
    @Bean
    @Primary
    @ConditionalOnExpression("${spring.inmemdb:true}")
    public DataSource inMemoryDataSource(){
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.url("jdbc:h2:mem:jishonotes");
        builder.username("sa");
        builder.username("password");
        return builder.build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.data.postgres")
    public DataSource postgresDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
