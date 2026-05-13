package com.souemilio.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

 // desativando o bean não recomendado pelo Spring
  //   @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    //Modelo recomendado pelo Spring

    /**
     * configuracao Hikari
     * https://github.com/brettwooldridge/HikariCP
     * @return
     */
    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10); //máximo de conexões liberadas
        config.setMinimumIdle(1); //tamano inicial do pool
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600000); //600 mil ms (10 min)
        config.setConnectionTimeout(100000); //timeout pra conseguir uma conexao
        config.setConnectionTestQuery("SELECT 1"); //query de teste

        return new HikariDataSource(config);
    }
}


