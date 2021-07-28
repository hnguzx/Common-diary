package pers.guzx.notice.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.common.serviceImpl.BaseServiceImpl;
import pers.guzx.notice.entity.SysMessage;
import pers.guzx.notice.handle.EmailHandle;
import pers.guzx.notice.mapper.MsgMapper;
import pers.guzx.notice.service.MsgService;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 9:57
 * @describe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MsgServiceImpl extends BaseServiceImpl<SysMessage> implements MsgService {

    @Resource
    private MsgMapper msgMapper;

    @Resource
    private JavaMailSenderImpl mailSender;

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${spring.mail.username}")
    public String sender;

    @Override
    public BaseMapper<SysMessage> getMapper() {
        return this.msgMapper;
    }

    @Resource
    private EmailHandle emailHandle;

    @Override
    public int save(SysMessage message) {
        int insert = msgMapper.insert(message);
        return insert;
    }

    @Override
    public int update(SysMessage message) {
        int update = msgMapper.updateByPrimaryKeySelective(message);
        return update;
    }

    @Override
    public Integer sendMail(SysMessage message) {
        emailHandle.checkEmail(message);
        MimeMessageHelper mimeMessageHelper = emailHandle.buildBaseEmail(message);

        try {
            mimeMessageHelper.setFrom(sender);
            MimeMessage mimeMessage = emailHandle.buildVerificationCode("验证码", mimeMessageHelper);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        int save = save(message);
        return save;
    }

    @Async
    public String sendCode(String email) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        String code = emailHandle.getVerifyCode();
        redisTemplate.opsForValue().set("registry:" + email, code, 600, TimeUnit.SECONDS);
        log.info("code：" + code);
        try {
            messageHelper.setFrom(sender);
            messageHelper.setTo(email);
            messageHelper.setText(code);
            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return code;
    }
}
