package com.springcloud.book.management.service;

import com.springcloud.book.management.domain.JournalNavigationMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-10-12
 */
public interface JournalNavigationMenuService extends IService<JournalNavigationMenu> {

    List<JournalNavigationMenu> getJournalsNavigationSubmenuByParentId(String menuParentId);

    void addJournalNavigationMenu(JournalNavigationMenu journalNavigationMenu);

    void deleteJournalNavigationMenu(String menuId) throws Exception;
}
