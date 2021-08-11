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
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/12 19:09
 * @describe
 */
@Data
@GroupSequence({Group.Add.class, Group.Update.class, SysMessage.class})
public class SysMessage {

    @Id
    @GeneratedValue(generator = "JDBC")
    @NotNull(message = "id不能为空", groups = {Group.Update.class})
    private Integer msgId;

    @NotBlank(message = "msgType不能为空", groups = {Group.Add.class})
    private String msgType;

    private String sender;

    @NotBlank(message = "receiver不能为空", groups = {Group.Add.class})
    private String receiver;

    @NotBlank(message = "content不能为空", groups = {Group.Add.class})
    private String content;
}
