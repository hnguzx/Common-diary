package pers.guzx.demo.serviceImpl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.common.serviceImpl.BaseServiceImpl;
import pers.guzx.demo.entity.CdUser;
import pers.guzx.demo.mapper.CdUserMapper;
import pers.guzx.demo.service.CdUserService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/5 10:54
 * @describe
 */
@Service
@Slf4j
public class CdUserServiceImpl extends BaseServiceImpl<CdUser> implements CdUserService<CdUser> {

    @Resource
    private CdUserMapper userMapper;

    @Override
    public BaseMapper<CdUser> getMapper() {
        return this.userMapper;
    }

    @Override
    public List<CdUser> findUserList(CdUser user, int pageNum, int pageSize) {
        RowBounds bounds = new RowBounds(pageSize, pageNum);
        Example example = new Example(CdUser.class);
        Example.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(user.getUserName())) {
            criteria.andLike("userName", "%" + user.getUserName() + "%");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<CdUser> cdUsers = userMapper.selectByExampleAndRowBounds(example, bounds);
        return cdUsers;
    }

}
