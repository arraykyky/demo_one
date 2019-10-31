package com.springcloud.book.foreign.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.SeniorLimiteConditions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.book.foreign.domain.vo.SeniorLimiteConditionsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author grl
 * @since 2019-09-10
 */
public interface SeniorLimiteConditionsDao extends BaseMapper<SeniorLimiteConditions> {

    List<SeniorLimiteConditions> selectList(SeniorLimiteConditionsVO conditionsVO);

    List<SeniorLimiteConditions> selectPage(Page page,@Param("pd") SeniorLimiteConditionsVO conditionsVO);
}
