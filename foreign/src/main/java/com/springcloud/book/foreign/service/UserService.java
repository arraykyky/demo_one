package com.springcloud.book.foreign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.User;
import com.springcloud.book.foreign.domain.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-07-30
 */
public interface UserService extends IService<User> {
    //通过Ella对应的用户id获取用户信息
    User getUserByEllaUserId(UserVO userVO) throws Exception;
    //获取用户的机构信息
    List<Map> getUserUnitList(UserVO userVO) throws Exception;
    //通过用户id更新用户信息
    void updateUserById(UserVO userVO) throws Exception;
    //验证密码
    boolean passWordCheck(UserVO userVO)throws Exception;
    //修改密码
    void modifyPassWord(UserVO userVO) throws Exception;
    //openId获取用户信息
    User getUserByOpenId(User u);
    //手机号获取用户信息
    User getUserByPhoneNumber(User user);
    //更新用户数据
    Integer updateUser(User user);
}
