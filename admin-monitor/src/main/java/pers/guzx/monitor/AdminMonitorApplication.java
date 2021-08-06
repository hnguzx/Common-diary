package pers.guzx.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/2 17:25
 * @describe
 */
@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class AdminMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminMonitorApplication.class, args);
    }

}
