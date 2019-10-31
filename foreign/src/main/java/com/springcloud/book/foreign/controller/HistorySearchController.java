package com.springcloud.book.foreign.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.vo.HistorySearchVO;
import com.springcloud.book.foreign.service.HistorySearchService;
import com.springcloud.book.foreign.service.SolrSearchService;
import com.springcloud.book.foreign.util.SolrSearchExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  历史检索
 * </p>
 *
 * @author grl
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/history")
public class HistorySearchController extends BaseController{

    @Autowired
    private HistorySearchService historySearchService;

    @Autowired
    private SolrSearchService solrSearchService;

    /**
     * 历史检索列表
     * 通过当前用户的token获取其用户id对应的历史检索记录
     * @param page
     * @param historySearchVO 历史检索VO,包含token必要参数—— token 当前登录用户的标记，获取用户的id
     * @return
     */
    @GetMapping("/list")
    public Object getCurrentUserHistorySearch(Page page, HistorySearchVO historySearchVO){
        PageData pageData = new PageData();
        try {
            historySearchService.getCurrentUserHistorySearchPage(page,historySearchVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.DATA,page);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        } catch (Exception e) {
            e.printStackTrace();
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 历史检索列表删除
     * @param historySearchVO 历史检索VO,包含searchesId必要参数—— searchesId 历史检索记录id
     * @return
     */
    @PostMapping("/delete_id")
    public Object deleteHistoryListSearch(HistorySearchVO historySearchVO){
        PageData pageData = new PageData();
        try {
            historySearchService.deleteHistorySearchBySearchesId(historySearchVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 历史检索清空
     * @param historySearchVO 历史检索VO,包含token必要参数—— token 当前登录用户的标记，以获取用户的id
     *                        设置searchesUserid的值—— searchesUserid 当前用户id
     * @return
     */
    @PostMapping("/clean_all")
    public Object cleanAllHistorySearch(HistorySearchVO historySearchVO){
        PageData pageData = new PageData();
        try {
            historySearchService.cleanAllHistorySearch(historySearchVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
        }catch (Exception e){
            e.printStackTrace();
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 检索
     * @param page
     * @param historySearchVO 历史检索VO,包含ext必要参数
     * @return
     */
    @GetMapping("/get_exp")
    public Object getHistoryList(Page page,HistorySearchVO historySearchVO){
        PageData pageData = new PageData();
        //解析exp封装检索条件集合，通过solr多字段检索通用方法获取结果
        List<Map> listMap = SolrSearchExpression.analysisExp(historySearchVO.getExt());
        this.solrMoreFieldsSearch(false,page,historySearchVO.getToken(),listMap,solrSearchService,pageData,null);
        return pageData;
    }

    /**
     * 策略检索
     * @return
     */
    @PostMapping("/get_some_exp")
    public Object getSearch(Page page,@RequestBody HistorySearchVO historySearchVO,String token){
        PageData pageData = new PageData();
        List<String> exps = historySearchVO.getSearcheExps();
        List<String> cons = historySearchVO.getCons();
        if (exps != null && !exps.isEmpty()){
            if (exps.size()>1){
                if (cons!=null && exps.size() -1 == cons.size()){
                    List<Map> listMap = SolrSearchExpression.analysisMoreExps(exps,cons);
                    this.solrMoreFieldsSearch(false,page,historySearchVO.getToken(),listMap,solrSearchService,pageData,null);
                }else {
                    pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
                    pageData.put(OverallConfig.MSG,"检索表达式数与连接词数不匹配");
                    pageData.put(OverallConfig.DATA,null);
                }
            }else {
                List<Map> listMap = SolrSearchExpression.analysisMoreExps(exps,cons);
                this.solrMoreFieldsSearch(false,page,token,listMap,solrSearchService,pageData,null);
            }
        }
        return pageData;
    }


}
