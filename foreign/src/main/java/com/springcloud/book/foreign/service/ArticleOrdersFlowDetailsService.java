package com.springcloud.book.foreign.service;

import com.springcloud.book.foreign.domain.ArticleOrdersFlowDetails;
import com.baomidou.mybatisplus.extension.service.IService;

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
