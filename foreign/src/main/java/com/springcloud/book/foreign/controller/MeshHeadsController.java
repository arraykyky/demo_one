package com.springcloud.book.foreign.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.controller.webDeals.WebSolrDeals;
import com.springcloud.book.foreign.domain.MeshHeads;
import com.springcloud.book.foreign.domain.vo.MeshHeadsVO;
import com.springcloud.book.foreign.service.MeshHeadsService;
import com.springcloud.book.foreign.service.SolrSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 *  主题词
 * </p>
 *
 * @author grl
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/mesh_heads")
public class MeshHeadsController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(MeshHeadsController.class);

    @Autowired
    private MeshHeadsService meshHeadsService;

    @Autowired
    private SolrSearchService solrSearchService;

    /**
     * 通过当前父节点id获取其子节点
     * @param meshHeadsVO 主题词VO,包含menuParentId必要参数,默认值为“0”—— menuParentId 主题词菜单父节点id
     * @return
     */
    @GetMapping("/menu")
    public Object getMeshHeadsMenuByMenuParentId(MeshHeadsVO meshHeadsVO){
        PageData pageData = new PageData();
        try {
            List<MeshHeads> list = meshHeadsService.getMeshHeadsMenuByMenuParentId(meshHeadsVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 通过检索词检索获取主题词列表(数据库全文索引实现)
     * @param page
     * @param meshHeadsVO 主题词VO,包含descriptorName必要参数 —— descriptorName 主题词
     * @return
     */
    @GetMapping("/search")
    public Object getSearchMeshHeads(Page page,MeshHeadsVO meshHeadsVO){
        PageData pageData = new PageData();
        try {
            meshHeadsService.getSearchMeshHeads(page,meshHeadsVO);
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
     * 主题词 ---- 通过主题词id获取主题词详细信息
     * @param  meshHeadsVO 主题词VO,包含id必要参数 —— id 主题词菜单主键id
     * @return
     */
    @GetMapping("/info_id")
    public Object getMeshHeadsInfo(MeshHeadsVO meshHeadsVO){
        PageData pageData = new PageData();
        try {
            MeshHeads meshHeads= meshHeadsService.getMeshHeadsInfoById(meshHeadsVO);
            Map<String,Object> map = new HashMap<>();
            if (meshHeads != null){
                map.putAll(JSON.parseObject(meshHeads.toString(),HashMap.class));
                String subMeshHeadsStr = meshHeads.getSubMeshHeads();
                String termsStr = meshHeads.getTerms();
                if (subMeshHeadsStr != null && !"".equals(subMeshHeadsStr)){
                    ArrayList<Map> subMeshHeadsList = JSON.parseObject(subMeshHeadsStr, new ArrayList<Map>().getClass());
                    map.put("subMeshHeads",subMeshHeadsList);
                }
                if (termsStr != null && !"".equals(termsStr)){
                    ArrayList<String> termsList = new ArrayList<>(Arrays.asList(termsStr.replace("[","").replace("]","").split(",")));
                    map.put("terms",termsList);
                }
            }
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 主题词
     * @param page
     * @param meshHeadsVO 主题词VO,包含start 起始年份/end 截至年份/qualifier 副主题词，可选参数
     * @return
     */
    @PostMapping("/abstracts")
    public Object getPageAbstractByMeshHeadIdAndYear(Page page, @RequestBody MeshHeadsVO meshHeadsVO,String token){
        PageData pageData = new PageData();
        List<Map> listMap = new ArrayList<>();
        //封装日期检索串
        String start = meshHeadsVO.getStart();
        String end = meshHeadsVO.getEnd();
        String timeValue = WebSolrDeals.solrDateSearchStr(start, end);
        Map<String, Object> time = WebSolrDeals.searchFieldMap("", "13", timeValue);
        listMap.add(time);
        if (meshHeadsVO.getQualifier() != null && !meshHeadsVO.getQualifier().isEmpty()){
            for (String str : meshHeadsVO.getQualifier()){
                Map<String, Object> qualifier = WebSolrDeals.searchFieldMap("1", "24", str);
                listMap.add(qualifier);
            }
        }
        this.solrMoreFieldsSearch(false,page,token,listMap,solrSearchService,pageData,null);
        return pageData;
    }

}

