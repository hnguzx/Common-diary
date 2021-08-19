package pers.guzx.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/19 14:44
 * @describe
 */
@Slf4j
@Component
public class DataSourceManager {
    @Resource
    private CustomizedConfigurationPropertiesBinder binder;

    @Resource
    private DataSourceProperties dataSourceProperties;

    public DruidDataSource createDataSource() {
        DruidDataSource dataSource = dataSourceProperties.initializeDataSourceBuilder().type(DruidDataSource.class).build();
        if (StringUtils.hasText(dataSourceProperties.getName())) {
//            dataSource.setPoolName();
            dataSource.setName(dataSourceProperties.getName());
        }
        Bindable<?> target = Bindable.of(DruidDataSource.class).withExistingValue(dataSource);
        this.binder.bind("spring.datasource.druid", target);
        return dataSource;
    }

    public DruidDataSource createAndTestDataSource() throws SQLException {
        DruidDataSource newDataSource = createDataSource();
        try {
            testConnection(newDataSource);
        } catch (SQLException ex) {
            log.error("Testing connection for data source failed: {}", newDataSource.getUrl(), ex);
            newDataSource.close();
            throw ex;
        }

        return newDataSource;
    }

    private void testConnection(DataSource dataSource) throws SQLException {
        //borrow a connection
        Connection connection = dataSource.getConnection();
        //return the connection
        connection.close();
    }
}
