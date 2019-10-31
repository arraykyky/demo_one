package com.springcloud.book.foreign.service;

import com.springcloud.book.foreign.domain.AbstractVolumsCountForJournal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.vo.VolumsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-08-12
 */
public interface AbstractVolumsCountForJournalService extends IService<AbstractVolumsCountForJournal> {
    //通过期刊id和年份，获取对应的期卷数据
    List<AbstractVolumsCountForJournal> getCurrentYearJournalVolumeIssue(VolumsVO volumsVO);
    //添加卷期数据
    void valumsCounts(List<String> journalIdList) throws Exception;
}
