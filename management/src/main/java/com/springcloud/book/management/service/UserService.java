package com.springcloud.book.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.management.domain.vo.UserVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-09-24
 */
public interface UserService extends IService<User> {
    //获取用户分页列表
    void findUserPage(Page page, UserVO userVO);
    //通过主键id获取用户信息
    User getUserById(String userId);
    //判断当前管理员可用点是否可以分配
    boolean isAbleAllot(UserVO userVO);
    //更新用户可用点数--分配
    void updateUserEnablePointNumberAllot(UserVO userVO);
    //更新用户信息
    void updateUserById(User adminUser);
    //更新用户可用点数--充值
    void updateUserEnablePointNumberVoucher(UserVO userVO);
    //通过用户的EllaUserId获取用户信息
    User selectUserByEllaUserId(String orderApplyUserId);
    //获取管理员数据集合
    List<User> getAdminUserList();
}
