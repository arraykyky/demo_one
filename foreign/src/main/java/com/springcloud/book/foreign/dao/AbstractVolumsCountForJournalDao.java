package com.springcloud.book.foreign.dao;

import com.springcloud.book.foreign.domain.AbstractVolumsCountForJournal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.foreign.domain.vo.VolumsVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-08-12
 */
public interface AbstractVolumsCountForJournalDao extends BaseMapper<AbstractVolumsCountForJournal> {

    List<AbstractVolumsCountForJournal> selectCurrentYearJournalVolumeIssue(VolumsVO volumsVO);

    AbstractVolumsCountForJournal findVolumeIsExist(AbstractVolumsCountForJournal av);
}
