package com.springcloud.book.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.domain.SystemEmail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-09-26
 */
public interface SystemEmailService extends IService<SystemEmail> {
    //获取所有系统邮箱
    List<SystemEmail> findAllSystemEmailDataList();
    //获取分页数据
    void getSystemEmailPage(Page page);
    //添加系统邮箱
    void addSystemEmail(SystemEmail systemEmail);
    //删除系统邮箱
    void deleteSystemEmail(Integer id);
}
