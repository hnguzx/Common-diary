package pers.guzx.notice.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.guzx.notice.entity.NoticeType;
import pers.guzx.notice.entity.SysMessage;
import pers.guzx.notice.handle.EmailHandle;
import pers.guzx.notice.mapper.MsgMapper;
import pers.guzx.notice.service.MsgService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 9:57
 * @describe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MsgServiceImpl implements MsgService {

    @Resource
    private MsgMapper msgMapper;

    @Resource
    private EmailHandle emailHandle;

    @Value("${spring.mail.username}")
    public String sender;

    @Override
    public SysMessage findById(int id) {
        return null;
    }

    @Override
    public SysMessage findBySender(String sender) {
        Example example = new Example(SysMessage.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sender", sender);
        List<SysMessage> sysMessages = msgMapper.selectByExample(example);
        if (sysMessages.size() > 0) {
            return sysMessages.get(0);
        }
        return null;
    }


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
    public int remove(int id) {
        return 0;
    }

    @Override
    public Integer sendMail(SysMessage message) throws MessagingException {
        emailHandle.sendEmail(message);
        return save(message);
    }

    @Override
    public Integer sendCode(String address, String type) throws MessagingException {
        log.info("发送" + type + " code:" + address);
        emailHandle.sendEmail(address, type);
        SysMessage message = new SysMessage();
        message.setMsgType(NoticeType.LOGIN.getType());
        message.setReceiver(address);
        message.setSender(sender);
        message.setContent(type);
        return save(message);
    }

}
