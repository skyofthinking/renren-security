package io.renren;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@ComponentScan
public class RenrenApplication {

    public static void main(String[] args) {
        SpringApplication.run(RenrenApplication.class, args);
    }

}
