package com.springcloud.book.foreign.service.impl;

import com.springcloud.book.foreign.domain.FullTextUploadFlow;
import com.springcloud.book.foreign.dao.FullTextUploadFlowDao;
import com.springcloud.book.foreign.service.FullTextUploadFlowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-09-19
 */
@Service
public class FullTextUploadFlowServiceImpl extends ServiceImpl<FullTextUploadFlowDao, FullTextUploadFlow> implements FullTextUploadFlowService {

    @Override
    public FullTextUploadFlow findFullTextInfoByAbstractId(String abstractId) {
        FullTextUploadFlow fullTextUploadFlow = new FullTextUploadFlow();
        fullTextUploadFlow.setAbstractId(abstractId);
        return baseMapper.selectByAbstractId(fullTextUploadFlow);
    }
}
