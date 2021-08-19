package pers.guzx.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.guzx.demo.entity.DynamicDataSource;

import javax.sql.DataSource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/19 11:45
 * @describe
 */
@Slf4j
@Configuration
public class DataSourceConfig {
    @Bean
    public DynamicDataSource dataSource(DataSourceManager dataSourceManager) {
        DataSource actualDataSource = dataSourceManager.createDataSource();
        return new DynamicDataSource(actualDataSource);
    }
}
