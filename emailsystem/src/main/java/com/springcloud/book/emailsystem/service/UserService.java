package com.springcloud.book.emailsystem.service;

import com.springcloud.book.emailsystem.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-10-09
 */
public interface UserService extends IService<User> {

    User getUserByEllaUserId(String orderApplyUserId);
}
