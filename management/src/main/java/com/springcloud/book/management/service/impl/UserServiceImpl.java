package com.springcloud.book.management.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.config.Constants;
import com.springcloud.book.management.config.redis.RedisServer;
import com.springcloud.book.management.domain.User;
import com.springcloud.book.management.dao.UserDao;
import com.springcloud.book.management.domain.UserPointRecord;
import com.springcloud.book.management.domain.vo.UserVO;
import com.springcloud.book.management.enums.PointAllotTypeEnum;
import com.springcloud.book.management.service.UserPointRecordService;
import com.springcloud.book.management.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.management.service.impl.deal.UserPointRecordDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-09-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private UserPointRecordService userPointRecordService;

    @Override
    public void findUserPage(Page page, UserVO userVO) {
        List<User> list = baseMapper.selectUserPage(page, userVO);
        page.setRecords(list);
    }

    @Override
    public User getUserById(String userId) {
        return baseMapper.selectById(userId);
    }

    @Override
    public boolean isAbleAllot(UserVO userVO) {
        //1.获取当前管理员信息
        String userId = (String)redisServer.get(Constants.getUserModuleCacheKeyForeignUserId(userVO.getToken()));
        User adminUser = this.getUserById(userId);
        return adminUser.getEnableUsePointNumber() >= userVO.getAllotPoint() ? true : false;
    }

    @Override
    @Transactional
    public void updateUserEnablePointNumberAllot(UserVO userVO) {
        //获取管理员信息
        String userId = (String)redisServer.get(Constants.getUserModuleCacheKeyForeignUserId(userVO.getToken()));
        User adminUser = this.getUserById(userId);
        User daoAdminUser = new User();
        daoAdminUser.setUserId(adminUser.getUserId());
        daoAdminUser.setEnableUsePointNumber(adminUser.getEnableUsePointNumber() - userVO.getAllotPoint());
        //管理员的剩余可用点数 - 分配点数更新
        this.updateUserById(daoAdminUser);
        //获取用户信息
        User user = this.getUserById(userVO.getUserId());
        User daoUser = new User();
        daoUser.setUserId(user.getUserId());
        daoUser.setEnableUsePointNumber(user.getEnableUsePointNumber() + userVO.getAllotPoint());
        //用户的剩余可用点数 + 分配点数更新
        this.updateUserById(daoUser);
        //添加管理员和用户的对账信息
        //管理员的对账信息
        UserPointRecord adminUserPointRecord = UserPointRecordDeal.getUserPointRecord(PointAllotTypeEnum.P_CONSUME.getDealNum(), userVO.getAllotPoint(), adminUser.getUserId(), adminUser.getEnableUsePointNumber(), daoAdminUser.getEnableUsePointNumber());
        userPointRecordService.addUserPointRecord(adminUserPointRecord);
        //用户的对账信息
        UserPointRecord userPointRecord = UserPointRecordDeal.getUserPointRecord(PointAllotTypeEnum.P_ALLOT.getDealNum(), userVO.getAllotPoint(), user.getUserId(), user.getEnableUsePointNumber(), daoUser.getEnableUsePointNumber());
        userPointRecordService.addUserPointRecord(userPointRecord);
    }

    @Override
    @Transactional
    public void updateUserEnablePointNumberVoucher(UserVO userVO) {
        //获取用户信息
        User user = this.getUserById(userVO.getUserId());
        User daoUser = new User();
        daoUser.setUserId(user.getUserId());
        daoUser.setEnableUsePointNumber(user.getEnableUsePointNumber() + userVO.getAllotPoint());
        //用户的剩余可用点数 + 分配点数更新
        this.updateUserById(daoUser);
        //用户的对账信息
        UserPointRecord userPointRecord = UserPointRecordDeal.getUserPointRecord(PointAllotTypeEnum.P_RECHARGE.getDealNum(), userVO.getAllotPoint(), user.getUserId(), user.getEnableUsePointNumber(), daoUser.getEnableUsePointNumber());
        userPointRecordService.addUserPointRecord(userPointRecord);
    }

    @Override
    public User selectUserByEllaUserId(String orderApplyUserId) {
        User user = new User();
        user.setEllaUserId(orderApplyUserId);
        return baseMapper.selectUserByEllaUserId(user);
    }

    @Override
    public List<User> getAdminUserList() {
        return baseMapper.selectAdminUserList();
    }

    @Override
    @Transactional
    public void updateUserById(User user) {
        baseMapper.updateById(user);
    }

}
