package pers.guzx.notice.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import pers.guzx.common.validation.Group;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/12 19:09
 * @describe
 */
@Data
@Table(name = "sys_message")
@Alias(value = "message")
@GroupSequence({Group.Add.class, Group.Update.class, SysMessage.class})
public class SysMessage {

    @NotNull(message = "id不能为空", groups = {Group.Update.class})
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "msg_id")
    private Integer msgId;

    @NotNull(message = "msgType不能为空", groups = {Group.Add.class, Group.Update.class})
    @Column(name = "msg_type")
    private Integer msgType;

    @Column(name = "sender")
    private String sender;

    @NotBlank(message = "receiver不能为空", groups = {Group.Add.class, Group.Update.class})
    @Column(name = "receiver")
    private String receiver;

    private Date sendTime;

    @NotBlank(message = "content不能为空", groups = {Group.Add.class, Group.Update.class})
    @Column(name = "content")
    private String content;
}
