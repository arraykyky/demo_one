package com.springcloud.book.foreign.service.impl;

import com.springcloud.book.foreign.domain.JournalNavigationMenu;
import com.springcloud.book.foreign.dao.JournalNavigationMenuDao;
import com.springcloud.book.foreign.domain.vo.JournalNavigationMenuVO;
import com.springcloud.book.foreign.service.JournalNavigationMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-08-09
 */
@Service
public class JournalNavigationMenuServiceImpl extends ServiceImpl<JournalNavigationMenuDao, JournalNavigationMenu> implements JournalNavigationMenuService {

    @Override
    public List<JournalNavigationMenu> getJournalNavigationMenuByMenuParentId(JournalNavigationMenuVO journalClassificationMenuVO) {
        return baseMapper.selectJournalNavigationMenuByMenuParentId(journalClassificationMenuVO);
    }
}
