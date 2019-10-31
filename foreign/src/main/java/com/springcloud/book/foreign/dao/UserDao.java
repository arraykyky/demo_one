package com.springcloud.book.foreign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.User;
import com.springcloud.book.foreign.domain.vo.UserVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-07-30
 */
public interface UserDao extends BaseMapper<User> {
    User selectUserByEllaUserId(UserVO userVO);

    void updatePassWordByElleUserId(UserVO userVO);

    void saveUser(PageData pageData);

    void updataForeignDataUser(PageData pageData);

    PageData findForeignDataUser(PageData pageData);

    User selectUserByOpenId(User u);

    User selectUserByPhoneNumber(User u);
}
