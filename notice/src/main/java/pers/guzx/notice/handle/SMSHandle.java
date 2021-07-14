package pers.guzx.notice.handle;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 14:30
 * @describe
 */
@Component
public class SMSHandle {

    /** 产品密钥ID，产品标识 */
    private final static String SECRETID = "494e8ee71ae464ec841509a4de1feba4";
    /** 产品私有密钥，服务端生成签名信息使用，请严格保管，避免泄露 */
    private final static String SECRETKEY = "0ada50295bb5f75eb805ce32cbc5008b";
    /** 业务ID，易盾根据产品业务特点分配 */
    private final static String BUSINESSID = "YD00795538231798";
    /** 本机认证服务身份证实人认证在线检测接口地址 */
    private final static String API_URL = "https://sms.dun.163.com/v2/sendsms";

}
