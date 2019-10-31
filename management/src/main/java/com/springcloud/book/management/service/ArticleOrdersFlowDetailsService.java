package com.springcloud.book.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.management.domain.ArticleOrdersFlowDetails;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-09-19
 */
public interface ArticleOrdersFlowDetailsService extends IService<ArticleOrdersFlowDetails> {

    void addOrderFlowDetailsApply(ArticleOrdersFlowDetails flowDetails);
}
