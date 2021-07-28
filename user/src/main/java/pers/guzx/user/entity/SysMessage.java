package pers.guzx.user.entity;

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
public class SysMessage {

    private Integer msgId;
    private Integer msgType;
    private String sender;
    private String receiver;
    private Date sendTime;
    private String content;
}
