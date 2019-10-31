package com.springcloud.book.foreign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.SeniorLimiteConditions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.vo.SeniorLimiteConditionsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-09-10
 */
public interface SeniorLimiteConditionsService extends IService<SeniorLimiteConditions> {

    List<SeniorLimiteConditions> getLimiteConditions(SeniorLimiteConditionsVO conditionsVO) throws Exception;

    void getLimiteConditionsPage(Page page, SeniorLimiteConditionsVO conditionsVO) throws Exception;

    void addLimiteConditions(SeniorLimiteConditionsVO conditionsVO) throws Exception;
}
