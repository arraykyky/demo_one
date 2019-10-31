package com.springcloud.book.foreign.service;

import com.springcloud.book.foreign.domain.FullTextUploadFlow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-09-19
 */
public interface FullTextUploadFlowService extends IService<FullTextUploadFlow> {

    FullTextUploadFlow findFullTextInfoByAbstractId(String abstractId);
}
