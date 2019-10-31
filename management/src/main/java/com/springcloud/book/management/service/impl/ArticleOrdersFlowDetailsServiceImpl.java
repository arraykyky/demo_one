package com.springcloud.book.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springcloud.book.management.dao.ArticleOrdersFlowDetailsDao;
import com.springcloud.book.management.domain.ArticleOrdersFlowDetails;
import com.springcloud.book.management.service.ArticleOrdersFlowDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-09-19
 */
@Service
public class ArticleOrdersFlowDetailsServiceImpl extends ServiceImpl<ArticleOrdersFlowDetailsDao, ArticleOrdersFlowDetails> implements ArticleOrdersFlowDetailsService {

    @Override
    @Transactional
    public void addOrderFlowDetailsApply(ArticleOrdersFlowDetails flowDetails) {
        baseMapper.insertData(flowDetails);
    }
}
