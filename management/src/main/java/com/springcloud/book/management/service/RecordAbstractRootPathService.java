package com.springcloud.book.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.domain.RecordAbstractRootPath;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.management.domain.vo.UnzipCheckExportVO;
import com.springcloud.book.management.domain.vo.UnzipDataCheckVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-10-18
 */
public interface RecordAbstractRootPathService extends IService<RecordAbstractRootPath> {

    void getJournalVolumeIssueCountPage(Page page, UnzipDataCheckVO unzipDataCheckVO);

    void getUnzipDataJournalVolumeIssuePage(Page page, UnzipDataCheckVO unzipDataCheckVO);

    List<UnzipCheckExportVO> getJournalVolumeIssueCountList(UnzipDataCheckVO unzipDataCheckVO);
}
