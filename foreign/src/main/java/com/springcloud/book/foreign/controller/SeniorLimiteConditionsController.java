package com.springcloud.book.foreign.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.config.OverallConfig;
import com.springcloud.book.foreign.config.PageData;
import com.springcloud.book.foreign.domain.SeniorLimiteConditions;
import com.springcloud.book.foreign.domain.vo.SeniorLimiteConditionsVO;
import com.springcloud.book.foreign.service.SeniorLimiteConditionsService;
import com.springcloud.book.foreign.util.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author grl
 * @since 2019-09-11
 */
@RestController
@RequestMapping("/limite")
public class SeniorLimiteConditionsController {

    private Logger logger = LoggerFactory.getLogger(SeniorLimiteConditionsController.class);

    @Autowired
    private SeniorLimiteConditionsService conditionsService;

    /**
     * 获取全部数据
     * @param conditionsVO 限定对象VO 包含重要参数 limiteGroupNum 限定条件分组号(0000:人类/动物；0001:性别；0002:文献类型；0003:语种；0004:年龄)
     *                                                               如果该值为空，则获取全部类型的数据
     * @return
     */
    @GetMapping("/list_all")
    public Object getLimiteConditions(SeniorLimiteConditionsVO conditionsVO){
        PageData pageData = new PageData();
        try {
            List<SeniorLimiteConditions> conditionsList = conditionsService.getLimiteConditions(conditionsVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,conditionsList);
        }catch (Exception e){
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 获取分页数据 -- 管理端接口
     * @param page
     * @param conditionsVO 限定对象VO 包含重要参数 limiteGroupNum 限定条件分组号(0000:人类/动物；0001:性别；0002:文献类型；0003:语种；0004:年龄)
     *                                                              如果该值为空，则获取全部类型的数据
     * @return
     */
    @GetMapping("/list_page")
    public Object getLimiteConditionsPage(Page page,SeniorLimiteConditionsVO conditionsVO){
        PageData pageData = new PageData();
        try {
            conditionsService.getLimiteConditionsPage(page,conditionsVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,page);
        }catch (Exception e){
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    /**
     * 添加限定条件 -- 管理端接口
     * @param conditionsVO 限定对象VO 包含重要参数 limiteFieldNum:限定条件检索字段号
     *                                               limiteEnWorld:限定条件英文检索值
     *                                               limiteChWorld:限定条件中文描述词
     *                                               limiteGroupNum:限定条件分组号(0000:人类/动物；0001:性别；0002:文献类型；0003:语种；0004:年龄)
     *                                               limiteGroupName:限定条件分组名(人类/动物；性别；文献类型；语种；年龄)
     * @return
     */
    @PostMapping("/add")
    public Object addLimiteConditions(SeniorLimiteConditionsVO conditionsVO){
        PageData pageData = new PageData();
        try {
            conditionsService.addLimiteConditions(conditionsVO);
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,"true");
        }catch (Exception e){
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

    @PostMapping("/add_import")
    public Object addLimiteConditions(MultipartFile file,String token){
        List errorlist = new ArrayList();
        PageData pageData = new PageData();
        try {
            Map<String,String> titleValueMap = new HashMap<>();
            titleValueMap.put("solr字段号","limiteFieldNum");
            titleValueMap.put("限定英文值","limiteEnWorld");
            titleValueMap.put("限定中文描述","limiteChWorld");
            titleValueMap.put("限定分组号","limiteGroupNum");
            titleValueMap.put("限定分组名","limiteGroupName");
            List<Map<String, String>> list = ExcelUtil.readXlsToListMap(file, titleValueMap);
            for (Map<String,String> map : list){
                try {
                    SeniorLimiteConditionsVO conditionsVO = JSON.parseObject(JSON.toJSON(map).toString(), SeniorLimiteConditionsVO.class);
                    conditionsService.addLimiteConditions(conditionsVO);
                }catch (Exception e){
                    e.printStackTrace();
                    errorlist.add(map.get("limiteEnWorld").toString() + ":" + e.getMessage() + "@行号:" + (list.indexOf(map) + 2));
                }
            }
            pageData.put(OverallConfig.CODE,OverallConfig.SUCCESS);
            pageData.put(OverallConfig.MSG,OverallConfig.SUCCESS_MSG);
            pageData.put(OverallConfig.DATA,errorlist);
        }catch (Exception e){
            pageData.put(OverallConfig.CODE,OverallConfig.ERROR);
            pageData.put(OverallConfig.MSG,e.getMessage());
        }
        return pageData;
    }

}

