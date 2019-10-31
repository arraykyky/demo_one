package com.springcloud.book.management.dao;

import com.springcloud.book.management.domain.JournalNavigationMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-10-12
 */
public interface JournalNavigationMenuDao extends BaseMapper<JournalNavigationMenu> {

    List<JournalNavigationMenu> selectJournalsNavigationSubmenuByParentId(JournalNavigationMenu journalNavigationMenu);

    void insertJournalNavigationMenu(JournalNavigationMenu journalNavigationMenu);

    void updateJournalNavigationMenuByMenuId(JournalNavigationMenu journalNavigationMenu);
}
