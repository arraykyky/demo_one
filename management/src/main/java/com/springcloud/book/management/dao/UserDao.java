package com.springcloud.book.management.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.management.domain.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-09-24
 */
public interface UserDao extends BaseMapper<User> {

    List<User> selectUserPage(Page page, @Param("pd") UserVO userVO);

    User selectUserByEllaUserId(User user);

    List<User> selectAdminUserList();

}
