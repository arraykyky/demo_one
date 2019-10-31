package com.springcloud.book.emailsystem.service.impl;

import com.springcloud.book.emailsystem.domain.User;
import com.springcloud.book.emailsystem.dao.UserDao;
import com.springcloud.book.emailsystem.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-10-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Override
    public User getUserByEllaUserId(String orderApplyUserId) {
        User user = new User();
        user.setEllaUserId(orderApplyUserId);
        return baseMapper.selectUserByEllaUserId(user);
    }
}
