package com.springcloud.book.foreign.controller;


import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.AbstractVolumsCountForJournal;
import com.springcloud.book.foreign.domain.vo.VolumsVO;
import com.springcloud.book.foreign.service.AbstractVolumsCountForJournalService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author grl
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/volums")
public class AbstractVolumsCountForJournalController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(AbstractVolumsCountForJournalController.class);
    @Autowired
    private AbstractVolumsCountForJournalService volumsCountForJournalService;

    @GetMapping("/current_year")
    public Object getCurrentYearJournalVolumeIssue(VolumsVO volumsVO){
        PageData pageData = new PageData();
        try {
            List<AbstractVolumsCountForJournal> volums = volumsCountForJournalService.getCurrentYearJournalVolumeIssue(volumsVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,volums);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    //solr数据mysql化
    @RequestMapping("/issue_count")
    public Object countAbstractVolumDataInJournal(String journalIds) throws Exception {
        PageData pageData = new PageData();
        try {
            if (StringUtils.isNotBlank(journalIds)){
                List<String> journalIdList = new ArrayList<>(Arrays.asList(journalIds.split(";")));
                volumsCountForJournalService.valumsCounts(journalIdList);
                pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
                pageData.put(OverallConfig.DATA,true);
                pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            }
        }catch (Exception e){
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }
}

