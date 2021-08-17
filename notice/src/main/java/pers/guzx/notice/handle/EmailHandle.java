package pers.guzx.notice.handle;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.exception.BaseException;
import pers.guzx.common.util.EmailUtil;
import pers.guzx.notice.entity.NoticeType;
import pers.guzx.notice.entity.SysMessage;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.UUID;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 14:29
 * @describe
 */
@Slf4j
@Component
@EnableAsync
public class EmailHandle {

    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${spring.mail.username}")
    public String sender;

    @Async
    public void sendEmail(SysMessage message) throws MessagingException {
        MimeMessageHelper mimeMessageHelper = buildEmail(message.getReceiver());
        setText(mimeMessageHelper, NoticeType.OTHER.getType(), message.getContent(), message.getReceiver());
        mailSender.send(mimeMessageHelper.getMimeMessage());
    }

    @Async
    public void sendEmail(String address, String noticeType) throws MessagingException {
        MimeMessageHelper mimeMessageHelper = buildEmail(address);
        setText(mimeMessageHelper, noticeType, null, address);
        mailSender.send(mimeMessageHelper.getMimeMessage());
    }

    public MimeMessageHelper buildEmail(String address) throws MessagingException {
        checkEmail(address);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom(sender);
        messageHelper.setTo(address);
        return messageHelper;
    }

    public void setText(MimeMessageHelper mimeMessageHelper, String noticeType, String content, String address) throws MessagingException {
        mimeMessageHelper.setSubject(noticeType);
        switch (noticeType) {
            case "login":
                mimeMessageHelper.setText(getLoginCode(address), true);
                break;
            case "registry":
                mimeMessageHelper.setText(getRegistryCode(address), true);
                break;
            default:
                mimeMessageHelper.setText(content, false);
                break;
        }
    }

    public String getCode(NoticeType type, String address) {
        UUID uuid = UUID.randomUUID();
        String code = uuid.toString().substring(0, 6);
        redisTemplate.opsForValue().set(type.getType() + ":" + address, code);
        return code;
    }

    public String getRegistryCode(String address) {
        return "<p><h3>验证码:" + getCode(NoticeType.REGISTRY, address) + "</h3>您正在注册成为用户，" +
                "请在10分钟内完成注册。若非本人操作，请忽略。</p>";
    }

    public String getLoginCode(String address) {
        return "<p><h3>验证码:" + getCode(NoticeType.LOGIN, address) + "</h3>您正在登录阅读，" +
                "若非本人操作，请忽略。</p>";
    }

    public void checkEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new BaseException(ErrorCode.MSG_RECEIVER_NOT_FOUND);
        }
        if (!EmailUtil.isEmail(email)) {
            throw new BaseException(ErrorCode.MSG_EMAIL_FORMAT_ERROR);
        }
    }
}
