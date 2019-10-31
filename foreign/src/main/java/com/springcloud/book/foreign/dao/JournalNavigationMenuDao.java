package com.springcloud.book.foreign.dao;

import com.springcloud.book.foreign.domain.JournalNavigationMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.foreign.domain.vo.JournalNavigationMenuVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-08-09
 */
public interface JournalNavigationMenuDao extends BaseMapper<JournalNavigationMenu> {

    List<JournalNavigationMenu> selectJournalNavigationMenuByMenuParentId(JournalNavigationMenuVO journalClassificationMenuVO);
}
