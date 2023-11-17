package pers.guzx.uaa.serviceImpl;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

/**
 * @author 25446
 */
public class ClientDetailServiceImpl implements ClientDetailsService {
    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return null;
    }
}
