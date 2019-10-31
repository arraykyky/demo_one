package com.springcloud.book.management.dao;

import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.domain.Journal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-10-21
 */
public interface JournalDao extends BaseMapper<Journal> {

    List<PageData> getJournalMenuList();

}
