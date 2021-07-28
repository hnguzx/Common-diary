package pers.guzx.demo.service;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import pers.guzx.common.service.BaseService;
import pers.guzx.common.serviceImpl.BaseServiceImpl;
import pers.guzx.demo.entity.CdUser;
import pers.guzx.demo.mapper.CdUserMapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/5 10:54
 * @describe
 */
public interface CdUserService extends BaseService<CdUser> {
    public List<CdUser> findUserList(CdUser user,int pageNum,int pageSize);
}
