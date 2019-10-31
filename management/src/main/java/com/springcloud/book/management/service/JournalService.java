package com.springcloud.book.management.service;

import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.domain.Journal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-10-21
 */
public interface JournalService extends IService<Journal> {

    List<PageData> getJournalMenuList();

}
