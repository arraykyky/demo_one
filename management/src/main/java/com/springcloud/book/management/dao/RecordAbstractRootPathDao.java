package com.springcloud.book.management.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.domain.RecordAbstractRootPath;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.management.domain.vo.UnzipCheckExportVO;
import com.springcloud.book.management.domain.vo.UnzipDataCheckVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-10-18
 */
public interface RecordAbstractRootPathDao extends BaseMapper<RecordAbstractRootPath> {

    List<PageData> selectJournalVolumeIssueCountPage(Page page, @Param("pd") UnzipDataCheckVO unzipDataCheckVO);

    List<RecordAbstractRootPath> selectUnzipDataJournalVolumeIssuePage(Page page, @Param("pd") UnzipDataCheckVO unzipDataCheckVO);

    List<UnzipCheckExportVO> selectJournalVolumeIssueCountList(@Param("pd") UnzipDataCheckVO unzipDataCheckVO);
}
