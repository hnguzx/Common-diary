package pers.guzx.user.serviceImpl;

import org.springframework.stereotype.Service;
import pers.guzx.user.entity.SysScheduled;
import pers.guzx.user.entity.SysUserDetails;
import pers.guzx.user.mapper.SysScheduledMapper;
import pers.guzx.user.service.SysScheduledService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/6 15:14
 * @describe
 */
@Service
public class SysScheduledServiceImpl implements SysScheduledService {

    @Resource
    private SysScheduledMapper scheduledMapper;

    @Override
    public SysScheduled findById(int id) {
        return null;
    }

    @Override
    public int save(SysScheduled sysScheduled) {
        return 0;
    }

    @Override
    public int update(SysScheduled sysScheduled) {
        return 0;
    }

    @Override
    public int remove(int id) {
        return 0;
    }

    @Override
    public List<SysScheduled> getAll() {
        return scheduledMapper.selectAll();
    }

    @Override
    public SysScheduled getByScheduledKey(String key) {
        Example example = new Example(SysScheduled.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cronKey", key);
        List<SysScheduled> sysScheduleds = scheduledMapper.selectByExample(example);
        if (sysScheduleds.size() > 0) {
            return sysScheduleds.get(0);
        }
        return null;
    }
}
