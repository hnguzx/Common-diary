package pers.guzx.demo.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Administrator
 */
@ApiModel
@Alias(value = "user")
@Data
@Table(name = "cd_user")
public class CdUser {

    @Id
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_birthday")
    private Date userBirthday;
    @Column(name = "user_sex")
    private String userSex;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_state")
    private String userState;
    @Column(name = "user_phone")
    private String userPhone;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_head")
    private String userHead;
    @Column(name = "user_create_time")
    private Date userCreateTime;
    @Column(name = "user_last_login_time")
    private Date userLastLoginTime;
    @Column(name = "user_update_time")
    private Date userUpdateTime;
}