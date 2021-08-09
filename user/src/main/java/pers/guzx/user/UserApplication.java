package pers.guzx.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pers.guzx.user.schedule.GetTokenTask;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;

/**
 * @author Administrator
 */
@MapperScan(basePackages = "pers.guzx.user.mapper")
@SpringBootApplication(scanBasePackages = "pers.guzx")
@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
@EnableTransactionManagement
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        GetTokenTask getTokenTask = new GetTokenTask();
        getTokenTask.execute();
    }

}
