package com.springcloud.book.foreign.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.foreign.domain.SeniorLimiteConditions;
import com.springcloud.book.foreign.dao.SeniorLimiteConditionsDao;
import com.springcloud.book.foreign.domain.vo.SeniorLimiteConditionsVO;
import com.springcloud.book.foreign.service.SeniorLimiteConditionsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-09-10
 */
@Service
public class SeniorLimiteConditionsServiceImpl extends ServiceImpl<SeniorLimiteConditionsDao, SeniorLimiteConditions> implements SeniorLimiteConditionsService {

    @Override
    public List<SeniorLimiteConditions> getLimiteConditions(SeniorLimiteConditionsVO conditionsVO) throws Exception {
        return baseMapper.selectList(conditionsVO);
    }

    @Override
    public void getLimiteConditionsPage(Page page, SeniorLimiteConditionsVO conditionsVO) throws Exception {
        List<SeniorLimiteConditions> list = baseMapper.selectPage(page,conditionsVO);
        page.setRecords(list);
    }

    @Override
    public void addLimiteConditions(SeniorLimiteConditionsVO conditionsVO) throws Exception {
        boolean isNotEmpty = SeniorLimiteConditionsVO.checkSeniorLimiteConditionsNecessaryFieldIsNotEmpty(conditionsVO,"limiteId");
        if (isNotEmpty){
            baseMapper.insert(conditionsVO);
        }else {
            throw new RuntimeException("添加数据必要字段不能为空");
        }
    }
}
