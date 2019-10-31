package com.springcloud.book.foreign.service;

import com.springcloud.book.foreign.domain.Journal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.vo.JournalNavigationMenuVO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-08-09
 */
public interface JournalService extends IService<Journal> {

    //获取当前期刊下的入库菜单(年份-文献数量)
    List<Map> getJournalYearCounts(JournalNavigationMenuVO journalNavigationMenuVO) throws Exception;
}
