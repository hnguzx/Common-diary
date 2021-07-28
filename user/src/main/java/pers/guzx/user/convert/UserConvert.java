package pers.guzx.user.convert;

import lombok.Lombok;
import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;
import pers.guzx.common.convert.DTOConvert;
import pers.guzx.user.dto.UserDto;
import pers.guzx.user.entity.SysUserDetails;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021-07-28 下午 06:20
 * @describe
 */
@Component
public class UserConvert implements DTOConvert<SysUserDetails, UserDto> {
    @Override
    public UserDto convert(SysUserDetails userDetails) {
        DozerBeanMapper mapper = new DozerBeanMapper();
        return mapper.map(userDetails, UserDto.class);
    }
}
