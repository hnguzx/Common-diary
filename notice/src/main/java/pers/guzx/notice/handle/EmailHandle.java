package pers.guzx.notice.handle;

import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.exception.BaseException;
import pers.guzx.common.util.EmailUtil;
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
@Component
public class EmailHandle {

    @Resource
    private JavaMailSenderImpl mailSender;

    public void checkEmail(SysMessage message) {
        if (StringUtils.isEmpty(message.getReceiver())) {
            throw new BaseException(ErrorCode.MSG_RECEIVER_NOT_FOUND);
        }
        if (EmailUtil.isEmail(message.getReceiver())) {
            throw new BaseException(ErrorCode.MSG_EMAIL_FORMAT_ERROR);
        }
        if (StringUtils.isEmpty(message.getContent())) {
            throw new BaseException(ErrorCode.MSG_CONTENT_NOT_FOUND);
        }
    }

    public MimeMessageHelper buildBaseEmail(SysMessage message) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        try {
            messageHelper.setFrom(message.getSender());
            messageHelper.setTo(message.getReceiver());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return messageHelper;
    }

    public MimeMessage buildVerificationCode(String subject, MimeMessageHelper messageHelper) {
        try {
            messageHelper.setSubject(subject);
            messageHelper.setText("<p><h3>验证码:" + getVerifyCode() + "</h3>您正在注册成为用户，" +
                    "请在1分钟内完成注册。若非本人操作，请忽略。</p>", true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return messageHelper.getMimeMessage();
    }

    public String getVerifyCode(){
        UUID uuid = UUID.randomUUID();
        String verifyCode = uuid.toString().substring(0, 6);
        return verifyCode;
    }
}
