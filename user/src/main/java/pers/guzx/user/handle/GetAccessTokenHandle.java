package pers.guzx.user.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pers.guzx.user.schedule.GetTokenTask;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/11 14:21
 * @describe
 */
@Slf4j
@Component
public class GetAccessTokenHandle implements ApplicationRunner {

    @Resource
    private GetTokenTask getTokenTask;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getTokenTask.execute();
    }
}
