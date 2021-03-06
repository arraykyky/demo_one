package com.springcloud.book.emailsystem.dao;

import com.springcloud.book.emailsystem.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-10-09
 */
public interface UserDao extends BaseMapper<User> {
    User selectUserByEllaUserId(User user);
}
