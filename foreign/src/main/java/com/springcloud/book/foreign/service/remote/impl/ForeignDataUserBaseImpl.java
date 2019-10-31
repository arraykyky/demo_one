package com.springcloud.book.foreign.service.remote.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.dao.UserDao;
import com.springcloud.book.foreign.domain.User;
import com.springcloud.book.foreign.service.remote.ForeignDataUserBase;
import com.springcloud.book.foreign.util.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("foreignDataUserBase")
public class ForeignDataUserBaseImpl extends ServiceImpl<UserDao,User> implements ForeignDataUserBase {
    @Override
    @Transactional
    public void saveForeignDataUser(PageData pageData) {
        pageData.put("id", Tools.getUUID());
        baseMapper.saveUser(pageData);
    }

    @Override
    @Transactional
    public void updataForeignDataUser(PageData pageData) {
        PageData pd = getForeignDataUser(pageData);
        if (pd!=null){//存在当前ella用户信息 -- 更新
            baseMapper.updataForeignDataUser(pageData);
        }else {//不存在当前ella用户信息 -- 新增
            saveForeignDataUser(pageData);
        }
    }

    /**
     * 通过ella_user_id获取当前用户信息
     * @param pageData
     * @return
     */
    @Override
    @Transactional
    public PageData getForeignDataUser(PageData pageData) {
        return baseMapper.findForeignDataUser(pageData);
    }
}
