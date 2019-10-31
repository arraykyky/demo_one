package com.springcloud.book.foreign.service.impl;

import com.alibaba.fastjson.JSON;
import com.springcloud.book.foreign.controller.webDeals.WebSolrDeals;
import com.springcloud.book.foreign.domain.AbstractVolumsCountForJournal;
import com.springcloud.book.foreign.dao.AbstractVolumsCountForJournalDao;
import com.springcloud.book.foreign.domain.solr_been.AbstractSolr;
import com.springcloud.book.foreign.domain.vo.VolumsVO;
import com.springcloud.book.foreign.service.AbstractVolumsCountForJournalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.foreign.service.SolrSearchService;
import com.springcloud.book.foreign.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-08-12
 */
@Service
public class AbstractVolumsCountForJournalServiceImpl extends ServiceImpl<AbstractVolumsCountForJournalDao, AbstractVolumsCountForJournal> implements AbstractVolumsCountForJournalService {

    private Logger logger = LoggerFactory.getLogger(AbstractVolumsCountForJournalServiceImpl.class);

    @Autowired
    private SolrSearchService solrSearchService;

    @Override
    public List<AbstractVolumsCountForJournal> getCurrentYearJournalVolumeIssue(VolumsVO volumsVO) {
        return baseMapper.selectCurrentYearJournalVolumeIssue(volumsVO);
    }

    //获取当前期刊的文献出版时间统计数据--统计数据，加入数据库
    public Set<AbstractVolumsCountForJournal> getAbstractVolumes(String journalId) throws Exception{
        //每年统计结果集合
        Set<AbstractVolumsCountForJournal> volumsList = new HashSet<>();
        //获取改期刊下不同的版本:年-卷-期
        String sqStr = WebSolrDeals.ordinarySolrFreeWorldSearch("26", journalId, true);
        String[] queryField = {"journal_id","medline_date","volume","issue"};
        List<AbstractSolr> asList = solrSearchService.getAbstractSolrWithQueryField(sqStr,queryField);
        for (AbstractSolr as : asList){
            //年份和期刊id不存在的不统计
            if (as.getMedlineDate() != null && !"".equals(as.getMedlineDate()) && as.getJournalId() != null && !"".equals(as.getJournalId())){
                String volume = as.getVolume();
                if (volume != null && !"".equals(volume) && volume.length() >= 20){
                    int index = volume.indexOf(" ");
                    if (index > 0){
                        volume = volume.substring(0,index) + "...";
                    }
                }
                String issue = as.getIssue();
                if (issue != null && !"".equals(issue) && issue.length() >= 20){
                    int index = issue.indexOf(" ");
                    if (index > 0){
                        issue = issue.substring(0,index) + "...";
                    }
                }
                AbstractVolumsCountForJournal avcf = new AbstractVolumsCountForJournal(as.getJournalId(),
                        DateUtil.getCurrentDateYear(as.getMedlineDate()),volume,issue);
                volumsList.add(avcf);
            }
        }
        return volumsList;
    }

    @Override
    public void valumsCounts(List<String> journalIdList) throws Exception {
        for (String jId : journalIdList){
            List<AbstractVolumsCountForJournal> newAV = new ArrayList<>();
            logger.info("开始统计期刊"+jId+"的数据");
            Set<AbstractVolumsCountForJournal> volumsList = this.getAbstractVolumes(jId);
            logger.info("期刊"+jId+"的数据统计完成，开始添加数据库");
            for (AbstractVolumsCountForJournal av : volumsList){
                AbstractVolumsCountForJournal avE = baseMapper.findVolumeIsExist(av);
                if (avE != null){
                    logger.info("当前期卷已存在");
                }else {
                    newAV.add(av);
                }
            }
            if (!newAV.isEmpty()){
                this.saveBatch(new ArrayList<>(newAV));
            }
            logger.info("期刊"+jId+"的数据添加数据库完成");
        }
    }
}
