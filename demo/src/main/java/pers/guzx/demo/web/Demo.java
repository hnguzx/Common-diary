package pers.guzx.demo.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.bind.annotation.*;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.demo.entity.CdUser;
import pers.guzx.demo.service.CdUserService;
import pers.guzx.demo.serviceImpl.CdUserServiceImpl;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/2 16:55
 * @describe 基础测试controller
 */
@Api(tags = "测试用controller")
@RestController
@Slf4j
public class Demo {

    @ApiOperation(value = "测试demo的连通性")
    @GetMapping("/demo")
    public String demo() {
        return "success";
    }

    @ApiOperation(value = "测试接口返回Json格式数据")
    @GetMapping("/json")
    public JsonDto<Object> jsonDto() {
        return JsonDto.retOk();
    }

    @Resource
    private CdUserServiceImpl userService;

    @ApiOperation(value = "新增", notes = "用户名，用户生日，用户性别，用户密码，用户状态必输")
    @PostMapping("/insertUser")
    public JsonDto<String> insertUser(@RequestBody CdUser user) {
        int save = userService.save(user);
        return JsonDto.retOk();
    }

    @ApiOperation(value = "修改")
    @PutMapping("/updateUser")
    public JsonDto<String> updateUser(@RequestBody CdUser user) {
        int update = userService.update(user);
        return JsonDto.retOk();
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/deleteUser/{userId}")
    public JsonDto<String> deleteUser(@NotNull @PathVariable int userId) {
        int remove = userService.remove(userId);
        return JsonDto.retOk();
    }

    @ApiOperation(value = "查询单个信息")
    @GetMapping("/getUserInfo/{userId}")
    public JsonDto<CdUser> getUser(@NotNull @PathVariable int userId) {
        CdUser cdUser = userService.findById(userId);
        return JsonDto.retOk(cdUser);
    }

    @ApiOperation(value = "分页查询信息")
    @PostMapping("/getUserList/{pageSize}/{pageNum}")
    public JsonDto<PageInfo<CdUser>> getUserList(@RequestBody CdUser user, @PathVariable int pageSize, @PathVariable int pageNum) {
        List<CdUser> userList = userService.findUserList(user, pageNum, pageSize);
        PageInfo<CdUser> pageInfo = new PageInfo<>(userList);
        return JsonDto.retOk(pageInfo);
    }
}
