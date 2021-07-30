package pers.guzx.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pers.guzx.common.validation.Group;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021-07-28 下午 06:21
 * @describe
 */
@Data
public class UserDto {
    private Integer userId;
    private String username;
    private String birthday;
    private String sex;
    private String phone;
    private String email;
    private String role;
    private String token;
}
