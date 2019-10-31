package com.springcloud.book.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springcloud.book.management.domain.ArticleOrders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.springcloud.book.management.domain.vo.ArticleOrdersVO;
import com.springcloud.book.management.domain.vo.EmailSendVO;
import com.springcloud.book.management.domain.vo.OrderLogsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author grl
 * @since 2019-09-25
 */
public interface ArticleOrdersService extends IService<ArticleOrders> {

    //获取订单分页数据
    void getOrdersPage(Page page, ArticleOrdersVO articleOrdersVO);
    //通过id获取数据
    ArticleOrders getOrderById(String orderId);
    //订单处理操作
    void dealOrderById(ArticleOrdersVO articleOrdersVO) throws Exception;
    //根据id更新订单
    void updateOrderById(ArticleOrders daoArticleOrders);
    //发送全文邮件，更新订单邮件发送状态
    void updateOrderEmailStateForSend(EmailSendVO emailSendVO) throws Exception;
    //获取订单日志
    void getOrderLogesPage(Page page, OrderLogsVO orderLogsVO);
    //导出订单日志数据
    List<ArticleOrders> exportOrdersLogsList(OrderLogsVO orderLogsVO);
    //远程邮件发送业务
    void remoteEmailSend(String token, String orderId) throws Exception;
}
