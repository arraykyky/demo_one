package com.springcloud.book.management.service.impl;

import com.springcloud.book.management.domain.JournalNavigationMenu;
import com.springcloud.book.management.dao.JournalNavigationMenuDao;
import com.springcloud.book.management.service.JournalNavigationMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.management.util.Tools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-10-12
 */
@Service
public class JournalNavigationMenuServiceImpl extends ServiceImpl<JournalNavigationMenuDao, JournalNavigationMenu> implements JournalNavigationMenuService {

    @Override
    public List<JournalNavigationMenu> getJournalsNavigationSubmenuByParentId(String menuParentId) {
        JournalNavigationMenu journalNavigationMenu = new JournalNavigationMenu();
        journalNavigationMenu.setMenuParentId(menuParentId);
        return baseMapper.selectJournalsNavigationSubmenuByParentId(journalNavigationMenu);
    }

    @Override
    public void addJournalNavigationMenu(JournalNavigationMenu journalNavigationMenu) {
        //id存在，更新
        if (StringUtils.isNotBlank(journalNavigationMenu.getMenuId())){
            baseMapper.updateJournalNavigationMenuByMenuId(journalNavigationMenu);
        }
        //id不存在，新增
        else {
            journalNavigationMenu.setMenuId(Tools.getUUID());
            baseMapper.insertJournalNavigationMenu(journalNavigationMenu);
        }
    }

    @Override
    public void deleteJournalNavigationMenu(String menuId)throws Exception{
        //判断该菜单是否有子节点，有则不允许删除
        List<JournalNavigationMenu> submenu = this.getJournalsNavigationSubmenuByParentId(menuId);
        if (submenu!= null && !submenu.isEmpty()){
            throw new RuntimeException("若要删除此菜单，请先删除该菜单下所有的子菜单");
        }else {
            baseMapper.deleteById(menuId);
        }
    }
}
