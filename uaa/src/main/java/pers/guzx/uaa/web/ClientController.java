package pers.guzx.uaa.web;

import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.bind.annotation.*;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.dto.JsonDto;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/18 14:13
 * @describe
 */
@RestController
@RequestMapping("/client")
public class ClientController {
    @Resource
    private JdbcClientDetailsService jdbcClientDetailsService;

    @PostMapping("/add")
    public JsonDto<BaseClientDetails> addClient(@RequestBody BaseClientDetails clientDetails) {
        clientDetails.setClientId(UUID.randomUUID().toString().substring(0, 12));
        jdbcClientDetailsService.addClientDetails(clientDetails);
        return JsonDto.retOk(clientDetails);
    }

    @PatchMapping("/updatePassword")
    public JsonDto<BaseClientDetails> updateClient(@RequestBody Map<String, String> parameter) {
        String clientId = parameter.get("client_id");
        String clientSecret = parameter.get("client_secret");
        jdbcClientDetailsService.updateClientSecret(clientId, clientSecret);
        return JsonDto.retOk();
    }

    @GetMapping("/info/{clientId}")
    public JsonDto getClient(@PathVariable String clientId) {
        ClientDetails clientDetails = jdbcClientDetailsService.loadClientByClientId(clientId);
        if (clientDetails == null) {
            return JsonDto.retFail(ErrorCode.EMAIL_SEND_ERROR);
        }
        return JsonDto.retOk(clientDetails);
    }

    @DeleteMapping("/delete/{clientId}")
    public JsonDto deleteClient(@PathVariable String clientId) {
        jdbcClientDetailsService.removeClientDetails(clientId);
        return JsonDto.retOk();
    }
}
