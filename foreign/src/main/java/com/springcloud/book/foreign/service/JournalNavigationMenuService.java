package com.springcloud.book.foreign.service;

import com.springcloud.book.foreign.domain.JournalNavigationMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.vo.JournalNavigationMenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-08-09
 */
public interface JournalNavigationMenuService extends IService<JournalNavigationMenu> {

    //通过菜单的父节点id获取子节点
    List<JournalNavigationMenu> getJournalNavigationMenuByMenuParentId(JournalNavigationMenuVO journalClassificationMenuVO);
}
