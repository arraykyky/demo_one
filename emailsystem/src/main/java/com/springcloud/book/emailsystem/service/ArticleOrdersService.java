package com.springcloud.book.emailsystem.service;

import com.springcloud.book.emailsystem.bean.EmailVO;
import com.springcloud.book.emailsystem.domain.ArticleOrders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-10-09
 */
public interface ArticleOrdersService extends IService<ArticleOrders> {
    void sendEmailAttach(EmailVO emailVO) throws Exception;
    void sendEmailMoreRecipient(EmailVO emailVO) throws Exception;
    void sendEmailText(EmailVO emailVO) throws Exception;
}
