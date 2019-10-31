package com.springcloud.book.foreign.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.service.PubMedSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/pubmed")
public class PubMedSearchController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(PubMedSearchController.class);

    @Autowired
    private PubMedSearchService pubMedSearchService;

    /**
     * 快速检索/条件检索
     *      term 检索条件
     * 		size 每页显示条数			//默认10
     * 		current 当前页				//默认1
     * @param page
     * @return
     */
    @GetMapping({"/list","/quick"})
    public Object pubmed(Page page, HttpServletRequest request){
        PageData pageData = new PageData(request);
        try {
            pubMedSearchService.getPubMedPage(pageData,page);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * PubMed检索 详情
     *      id 对应摘要的主键id
     * @return
     */
    @GetMapping("/info")
    public Object info(HttpServletRequest request) throws Exception {
        PageData  pageData = new PageData(request);
        try {
            Map<String,String> map = pubMedSearchService.getInfo(pageData);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

}
