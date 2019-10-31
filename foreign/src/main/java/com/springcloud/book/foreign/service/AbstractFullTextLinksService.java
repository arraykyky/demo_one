package com.springcloud.book.foreign.service;

import com.springcloud.book.foreign.domain.AbstractFullTextLinks;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.foreign.domain.vo.AbstractFullTextLinksVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-08-16
 */
public interface AbstractFullTextLinksService extends IService<AbstractFullTextLinks> {
    //通过类型获取url
    AbstractFullTextLinks getFullTextLinksByUrlType(AbstractFullTextLinksVO fullTextLinksVO)throws Exception;
}
