package com.springcloud.book.management.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.config.PageData;
import com.springcloud.book.management.domain.RecordAbstractRootPath;
import com.springcloud.book.management.dao.RecordAbstractRootPathDao;
import com.springcloud.book.management.domain.vo.UnzipCheckExportVO;
import com.springcloud.book.management.domain.vo.UnzipDataCheckVO;
import com.springcloud.book.management.service.RecordAbstractRootPathService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-10-18
 */
@Service
public class RecordAbstractRootPathServiceImpl extends ServiceImpl<RecordAbstractRootPathDao, RecordAbstractRootPath> implements RecordAbstractRootPathService {

    @Override
    public void getJournalVolumeIssueCountPage(Page page, UnzipDataCheckVO unzipDataCheckVO) {
        List<PageData> list = baseMapper.selectJournalVolumeIssueCountPage(page,unzipDataCheckVO);
        page.setRecords(list);
    }

    @Override
    public void getUnzipDataJournalVolumeIssuePage(Page page, UnzipDataCheckVO unzipDataCheckVO) {
        List<RecordAbstractRootPath> list = baseMapper.selectUnzipDataJournalVolumeIssuePage(page,unzipDataCheckVO);
        page.setRecords(list);
    }

    @Override
    public List<UnzipCheckExportVO> getJournalVolumeIssueCountList(UnzipDataCheckVO unzipDataCheckVO) {
        return baseMapper.selectJournalVolumeIssueCountList(unzipDataCheckVO);
    }
}
