package com.springcloud.book.foreign.controller;


import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.JournalNavigationMenu;
import com.springcloud.book.foreign.domain.vo.JournalNavigationMenuVO;
import com.springcloud.book.foreign.service.JournalNavigationMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  期刊导航菜单
 * </p>
 *
 * @author grl
 * @since 2019-08-09
 */
@RestController
@RequestMapping("/journal_navigation_menu")
public class JournalNavigationMenuController {

    private Logger logger = LoggerFactory.getLogger(JournalNavigationMenuController.class);

    @Autowired
    private JournalNavigationMenuService journalNavigationMenuService;


    /**
     * 通过菜单的父标签id获取菜单列表
     * @param journalNavigationMenuVO 期刊学科分类菜单VO,包含menuParentId必要参数 —— menuParentId 菜单父节点id
     * @return
     */
    @GetMapping("/menu")
    public Object getJournalNavigationMenuByMenuParentId(JournalNavigationMenuVO journalNavigationMenuVO){
        PageData pageData = new PageData();
        try {
            List<JournalNavigationMenu> list = journalNavigationMenuService.getJournalNavigationMenuByMenuParentId(journalNavigationMenuVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        }
        return pageData;
    }

}

