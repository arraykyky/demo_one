package com.springcloud.book.foreign.service.impl;

import com.springcloud.book.foreign.config.exception.CustomException;
import com.springcloud.book.foreign.domain.AbstractFullTextLinks;
import com.springcloud.book.foreign.dao.AbstractFullTextLinksDao;
import com.springcloud.book.foreign.domain.vo.AbstractFullTextLinksVO;
import com.springcloud.book.foreign.enums.AbstractFullTextLinksEnum;
import com.springcloud.book.foreign.service.AbstractFullTextLinksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author grl
 * @since 2019-08-16
 */
@Service
public class AbstractFullTextLinksServiceImpl extends ServiceImpl<AbstractFullTextLinksDao, AbstractFullTextLinks> implements AbstractFullTextLinksService {

    @Override
    public AbstractFullTextLinks getFullTextLinksByUrlType(AbstractFullTextLinksVO fullTextLinksVO) throws Exception{
        if (fullTextLinksVO.getUrlContent() != null && !"".equals(fullTextLinksVO.getUrlContent())){
            String type = AbstractFullTextLinksEnum.getValue(fullTextLinksVO.getUrlType());
            if (type != null){
                AbstractFullTextLinks abstractFullTextLinks = new AbstractFullTextLinks();
                abstractFullTextLinks.setUrlType(type);
                return baseMapper.selectOne(abstractFullTextLinks);
            }else {
                throw new CustomException("urlType参数不能为空");
            }
        }else {
            throw new CustomException("urlContent参数不能为空");
        }
    }
}
