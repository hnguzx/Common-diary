package pers.guzx.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.guzx.common.dto.JsonDto;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/2 17:10
 * @describe
 */
@Configuration
public class JsonDtoAutoConfigure {

    @Bean
    public JsonDto jsonDto() {
        return new JsonDto();
    }
}
