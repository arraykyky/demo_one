package com.springcloud.book.management.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.domain.SystemEmail;
import com.springcloud.book.management.dao.SystemEmailDao;
import com.springcloud.book.management.service.SystemEmailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-09-26
 */
@Service
public class SystemEmailServiceImpl extends ServiceImpl<SystemEmailDao, SystemEmail> implements SystemEmailService {

    @Override
    public List<SystemEmail> findAllSystemEmailDataList() {
        return baseMapper.selectList(null);
    }

    @Override
    public void getSystemEmailPage(Page page) {
        baseMapper.selectPage(page,null);
    }

    @Override
    @Transactional
    public void addSystemEmail(SystemEmail systemEmail) {
        baseMapper.insert(systemEmail);
    }

    @Override
    @Transactional
    public void deleteSystemEmail(Integer id) {
        baseMapper.deleteById(id);
    }
}
